package com.video.service.impl;


import com.video.dao.UserRepository;
import com.video.domain.User;
import com.video.service.UserService;
import com.video.utils.Md5Utils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserRepository userRepository;

    @Resource
    Md5Utils md5Utils;


    /*判断用户名是否存在*/
    @Override
    public String findByRegisterName(User user) {
        List<User> all = userRepository.findAllByUEmailOrUNameOrUTell(user.getUserEmail(), user.getUserTell(), user.getUserName());
        if (all.size()>0){
            return "fail";
        }else {
            return "success";
        }
    }





    @Override
    public User save(User users) {
        //注册时进行MD5加密
        String password=md5Utils.getPassword(users.getUserName(),users.getUserPassword());
        users.setUserPassword(password);
        users.setUserStatue(0);
        return userRepository.save(users);
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).get();
    }



    @Override
    public String login(String loginName,String password) {
        //登录时进行MD5加密
        List<User> all = userRepository.findAllByUEmailOrUNameOrUTell(loginName, loginName, loginName);
        if(all.size()==0){
            return "用户名不存在";
        }else if (all.size()==1) {
            String pass = md5Utils.getPassword(all.get(0).getUserName(), password);
            if (all.get(0).getUserStatue()==1){
                if (all.get(0).getUserPassword().equals(pass)) {
                    return "success";
                }
            }else{
                return "亲，您的账号还未激活，请前往邮箱激活后再登录";
            }

        }else {
            return "登录失败";
        }
        return "登录失败";
    }
    /*
     *先查id
     */
    @Override
    public String updateUser(User user) {
        List<User> all = userRepository.findAllByUEmailOrUNameOrUTell(user.getUserName(), user.getUserEmail(), user.getUserTell());
        if (all.size()==1){
            userRepository.save(user);
            return "success";
        }else {
            return "fail";
        }

    }

    @Override
    public User findByName(String userName) {
        List<User> all = userRepository.findAllByUEmailOrUNameOrUTell(userName, userName, userName);
        return all.get(0);
    }

    @Override
    public String updatePassword(User user) {
        User user1 = userRepository.findById(user.getUserId()).get();
        String password=md5Utils.getPassword(user.getUserName(),user.getUserName());
        user1.setUserPassword(password);
        userRepository.save(user1);
        return "success";
    }

    @Override
    public User findByUId(int uId) {
        return userRepository.findById(uId).get();
    }

    @Override
    public User update(User user) {
        return userRepository.saveAndFlush(user);
    }

}
