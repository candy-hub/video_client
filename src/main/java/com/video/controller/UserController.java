package com.video.controller;

import com.aliyuncs.exceptions.ClientException;
import com.video.domain.Record;
import com.video.domain.User;
import com.video.response.LoginResponse;
import com.video.response.Pagination;
import com.video.service.UserService;
import com.video.utils.EmailUtils;
import com.video.utils.QiniuUploadUtils;
import com.video.utils.TelUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

@RestController
public class UserController {

    @Resource
    UserService userService;

    @Resource
    QiniuUploadUtils qiniuUploadUtils;

    @Resource
    EmailUtils emailUtils;

    @Resource
    TelUtils telUtils;

    @Resource(name = "redisTemplates")
    RedisTemplate redisTemplate;
    /*
    * 用户界面
    */

    /*注册*/
    @RequestMapping(value = "/userRegister",method = RequestMethod.POST)
    public String register(@RequestBody User user){
        String msg="";
        if (userService.findByRegisterName(user)!="success"){
            msg="用户名已存在";
            return msg;
        }
        /*if (!builderCode.equals(inputCode)){
            msg="验证码输入错误";
            return msg;
        }*/
        User save = userService.save(user);
        emailUtils.sendMail(save.getUserId());
        return "success";
    }

    /*邮箱激活成功之后的回调*/
    @RequestMapping(value = "/sendMail/{userId}",method = RequestMethod.GET)
    public String updateStatus(@PathVariable("userId")Integer userId){
        User user = userService.findByUserId(userId);
        user.setUserStatue(2);
        userService.update(user);
        return "激活成功";
    }

     /*登录*/
    @RequestMapping(value = "/userLogin",method = RequestMethod.POST)
    public String userLogin(@RequestBody LoginResponse loginResponse){
        System.out.println(loginResponse);
        return userService.login(loginResponse);
    }

    /*通过用户输入的登录名查找一个对象*/
    @RequestMapping(value = "/findUserByName/{loginName}",method = RequestMethod.POST)
    public User findByName(@PathVariable String loginName){
        return userService.findUserByLoginName(loginName);
    }

    /*用户完善修改个人信息通过id查用户*/
    @RequestMapping(value = "/findUserByUserId/{userId}",method = RequestMethod.GET)
    public User findById(@PathVariable("userId") Integer userId){
        return userService.findById(userId);
    }

    /*头像上传*/
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public String upload(MultipartFile file){

        if (Objects.isNull(file) || file.isEmpty()) {
            return "fail";
        }else {
            String path = qiniuUploadUtils.upload(file);
            return path;
        }
    }

    /*修改用户信息*/
    @RequestMapping(value = "/updateUser",method = RequestMethod.POST)
    public String updateUser(@RequestBody User user){
        userService.updateUser(user);
        return "success";
    }

    /*用户修改密码*/
    @RequestMapping(value = "/updatePassword",method = RequestMethod.POST)
    public String updatePassword(@RequestBody User user){
        //System.out.println(user);
        return userService.updatePassword(user);
    }

    /*新增/修改用户历史记录*/
    @RequestMapping(value = "/changeRecord",method = RequestMethod.POST)
    public Record addRecord(@RequestBody Record record){
        List<Record> all = userService.findRecordByUserIdAndVideoId(record.getUserId(), record.getVideoId());
        System.out.println(all);
        if (all!=null){
            Record record2 = all.get(0);
            record2.setVideoTime(record.getVideoTime());
            Record record1 = userService.ChangeRecord(record2);
            redisTemplate.opsForHash().put("user"+record.getUserId(), "video"+record.getVideoId(), record1);
            return record1;
        }else{
            Record record2 = userService.ChangeRecord(record);
            redisTemplate.opsForHash().put("user"+record.getUserId(), "video"+record.getVideoId(), record2);
            return record2 ;
        }
    }

    @RequestMapping(value = "/findRecordByVideoId/{videoId}/{userId}",method = RequestMethod.GET)
    public List<Record> findRecordByVideoId(@PathVariable("videoId")Integer videoId,@PathVariable("userId")Integer userId){
        List<Record> all = userService.findRecordByUserIdAndVideoId(userId, videoId);
        return all;

    }

    /*清空用户历史记录*/
    @RequestMapping(value = "/deleteAll/{userId}",method = RequestMethod.GET)
    public String batchDelete(@PathVariable("userId")Integer userId){
        List<Record> allRecord = userService.findUserAllRecord(userId);
        for (Record record:allRecord){
            userService.delete(record.getRecordId());
            redisTemplate.opsForHash().delete("user"+userId,"video"+record.getVideoId());
        }
        return "1";
    }

    /*删除用户历史记录*/
    @RequestMapping(value = "/deleteRecordByRecordId/{recordId}",method = RequestMethod.GET)
    public String delete(@PathVariable("recordId")Integer recordId){
        Record record = userService.findAllRecord(recordId);
        userService.delete(recordId);
        redisTemplate.opsForHash().delete("user"+record.getUserId(), "video"+record.getVideoId());
        return "1";
    }

    /*查看用户历史记录*/
    @RequestMapping(value = "/findUserAllRecord/{userId}/{page}/{size}",method = RequestMethod.GET)
    public Pagination findUserAllRecord(@PathVariable("userId") Integer userId, @PathVariable("page") Integer page, @PathVariable("size") Integer size){
        if (page<1){
            page=1;
        }
        Pagination pagination=new Pagination();

        Map<Object, Object> entries = redisTemplate.opsForHash().entries("user"+userId);
        List<Record> records=new LinkedList<Record>();
        if (entries.size()>0){
            //redis中存在才需要map遍历转list
            Collection<Object> values = entries.values();
            Iterator<Object> iterator = values.iterator();
            while(iterator.hasNext()){
                records.add((Record) iterator.next());
            }
            records.sort(new Comparator<Record>() {
                @Override
                public int compare(Record o1, Record o2) {
                    return o2.getRecordId()-o1.getRecordId();
                }
            });
            pagination.setTotal((long)records.size());
            List<Record> list=new ArrayList<>();
            for (int i=(page-1)*size;i<page*size &&i<records.size();i++){
                list.add(records.get(i));
            }
            //System.out.println(list.size());
            pagination.setList(list);
            return pagination;
        }else{
            List<Record> all = userService.findUserAllRecord(userId);
            pagination.setTotal((long)records.size());
            if (all!=null){
                for (Record record : all) {
                    redisTemplate.opsForHash().put("user"+record.getUserId(), "video"+record.getVideoId(),record);
                }
                List<Record> list=new ArrayList<>();
                for (int i=(page-1)*size;i<page*size;i++){
                    list.add(records.get(i));
                }
                System.out.println(list.size());
                pagination.setList(list);
                return pagination;
            }else {
                return null;
            }
        }
    }

    /*
     * 管理人员界面
     */

    /*用户信息展示*/
    @RequestMapping(value = "findAllUser",method = RequestMethod.POST)
    public List<User> findAllUser(){
        return userService.findAllUser();
    }

    /*修改用户状态*/
    @RequestMapping(value = "/updateUserStatue",method = RequestMethod.GET)
    public String updateUserStatue(@RequestParam Integer userId){
        userService.updateUserStatue(userId);
        return "修改成功";
    }

    //获取忘记密码的验证码
    @RequestMapping("/getCode/{tel}")
    public String getCode(@PathVariable("tel") String tel){
        try {
            String code = telUtils.sendCode(tel);
            if("success".equals(code)){
                return "1";
            }else {
                return "0";
            }
        } catch (ClientException e) {
            e.printStackTrace();
        }
       return null;
    }

    //验证手机验证码
    @RequestMapping("/checkCode/{code}/{tel}")
    public String checkCode(@PathVariable("code") String code,@PathVariable("tel") String tel){
        return userService.checkCode(code, tel);
    }

    //根据手机号查user对象
    @RequestMapping("/findUserByTel/{tel}")
    public User findUserByTel(@PathVariable("tel") String tel){
        return userService.findUserByTel(tel);
    }


}
