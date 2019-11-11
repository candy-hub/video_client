package com.video.controller;

import com.video.dao.UserRepository;
import com.video.domain.User;
import com.video.service.UserService;
import com.video.utils.EmailUtils;
import com.video.utils.QiniuUploadUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@RestController
public class UserController {

    @Resource
    UserRepository userRepository;

    @Resource
    UserService userService;

    @Resource
    QiniuUploadUtils qiniuUploadUtils;

    @Resource
    EmailUtils emailUtils;

   /* @RequestMapping(value = "/findAllUsers",method = RequestMethod.GET)
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    @RequestMapping(value = "/checkUser",method = RequestMethod.POST)
    public List<User> checkUser(@RequestBody User user){
        return userRepository.findAllByUNameAndUEmailAndUTell(user.getUserTell(),user.getUserEmail(),user.getUserName());
    }*/

    /*
     * 注册
     */
    @RequestMapping(value = "/userRegister",method = RequestMethod.POST)
    public String register(@RequestBody User user,String inputCode,String builderCode){
        String msg="";
        if (userService.findByRegisterName(user)!="success"){
            if (!builderCode.equals(inputCode)){
                return "验证码输入错误";
            }
            msg="用户名已存在";
            return msg;
        }else {
            User save = userService.save(user);
            emailUtils.sendMail(save.getUserId());
            return "success";
        }
    }

    @RequestMapping(value = "sendMail/{id}",method = RequestMethod.GET)
    public String updateStatus(@PathVariable("id")Integer id){
        User user = userService.findById(id);
        user.setUserStatue(1);
        userService.update(user);
        return "激活成功";
    }

    /*
     * 登录
     */
    @RequestMapping(value = "/userLogin",method = RequestMethod.POST)
    public String userLogin(@RequestParam String userName,String password){
        return userService.login(userName,password);
    }

    @RequestMapping(value = "/findUserByUid/{uId}",method = RequestMethod.GET)
    public User findById(@PathVariable("uId") int uId){
        return userService.findById(uId);
    }

    @RequestMapping(value = "/findUserByName/{uname}",method = RequestMethod.POST)
    public User findByName(@PathVariable String userName){
        return userService.findByName(userName);
    }

    @RequestMapping(value = "/updateUser",method = RequestMethod.POST)
    public String updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }

    @RequestMapping(value = "/updatePassword",method = RequestMethod.POST)
    public String updatePassword(@RequestBody User user){
        return userService.updatePassword(user);
    }

    @RequestMapping(value = "/findUserByUid/{uId}",method = RequestMethod.POST)
    public User findByUId(@PathVariable("uId") int uId){
        return userService.findByUId(uId);
    }

    /*小图上传*/
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public String upload(MultipartFile file){

        if (Objects.isNull(file) || file.isEmpty()) {
            return "fail";
        }else {
            String path = qiniuUploadUtils.upload(file);
            return path;
        }

    }
}
