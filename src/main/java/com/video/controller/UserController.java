package com.video.controller;

import com.video.dao.RecordRepository;
import com.video.dao.UserRepository;
import com.video.domain.Record;
import com.video.domain.User;
import com.video.domain.Video;
import com.video.response.LoginResponse;
import com.video.service.UserService;
import com.video.service.VideoService;
import com.video.utils.EmailUtils;
import com.video.utils.QiniuUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@RestController
public class UserController {

    @Resource
    UserService userService;

    @Resource
    QiniuUploadUtils qiniuUploadUtils;

    @Resource
    EmailUtils emailUtils;
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

    @RequestMapping(value = "/addRecord",method = RequestMethod.POST)
    public Record addRecord(@RequestBody Video video){
        Record record=new Record();
        record.setUserId(video.getUserId());
        record.setVideoId(video.getVideoId());
        record.setVideoPic(video.getVideoPic());
        userService.insertRecord(record);
        return record;
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
