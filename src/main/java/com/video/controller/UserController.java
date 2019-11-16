package com.video.controller;

import com.video.domain.Record;
import com.video.domain.User;
import com.video.response.LoginResponse;
import com.video.service.UserService;
import com.video.utils.EmailUtils;
import com.video.utils.QiniuUploadUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
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
        user.setUserStatue(1);
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

    /*新增用户历史记录*/
    @RequestMapping(value = "/addRecord",method = RequestMethod.POST)
    public String addRecord(@RequestBody Record record){
        System.out.println(record);
        /*userService.insertRecord(record);
        redisTemplate.opsForHash().put(record.getUserId(), record.getVideoId(), record);*/
        return "1";
    }

    /*批量删除用户历史记录*/
    @RequestMapping(value = "/batchDelete",method = RequestMethod.POST)
    public String batchDelete(@RequestBody List<Record> records){
        for (Record record:records){
            userService.delete(record.getRecordId());
            redisTemplate.opsForHash().delete(record.getUserId(), record.getVideoId());
        }
        return "1";
    }

    /*删除用户历史记录*/
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public String delete(@RequestBody Record record){
        userService.delete(record.getRecordId());
        redisTemplate.opsForHash().delete(record.getUserId(), record.getVideoId());
        return "1";
    }

    /*查看用户历史记录*/
    @RequestMapping(value = "/findUserAllRecord",method = RequestMethod.POST)
    public List<Record> findUserAllRecord(Integer userId){
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(userId);
        List<Record> records=new LinkedList<Record>();
        if (entries.size()>0){
            Collection<Object> values = entries.values();
            Iterator<Object> iterator = values.iterator();
            while(iterator.hasNext()){
                records.add((Record) iterator.next());
            }
            /*records.sort(new Comparator<Record>() {
                @Override
                public int compare(Record o1, Record o2) {
                    return o1.getVideoId()-o2.getVideoId();
                }
            });*/
            return records;
        }else{
            List<Record> all = userService.findUserAllRecord(userId);
            if (all!=null){
                Map<String, Object> map = new TreeMap<>();
                for (Record record : all) {
                    redisTemplate.opsForHash().put(userId, record.getVideoId(), record);
                }
                return all;
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

    /*重置用户密码*/
    @RequestMapping(value = "/resetPassword",method = RequestMethod.GET)
    public String resetPassword(@RequestParam Integer userId){
        userService.resetPassword(userId);
        return "重置成功";
    }

    /*修改用户状态*/
    @RequestMapping(value = "/updateUserStatue",method = RequestMethod.GET)
    public String updateUserStatue(@RequestParam Integer userId){
        userService.updateUserStatue(userId);
        return "修改成功";
    }
}
