package com.video.service.impl;


import com.video.dao.UserRepository;
import com.video.domain.User;
import com.video.service.UserService;
import com.video.utils.Md5Utils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserRepository userRepository;

    @Resource
    Md5Utils md5Utils;

    /*
    * 用户界面
    */


    /*判断用户名是否存在*/
    @Override
    public String findByRegisterName(User user) {
        List<User> all = userRepository.findAllByUserTellOrUserEmailOrUserName(user.getUserEmail(), user.getUserTell(), user.getUserName());
        if (all.size()>0){
            return "fail";
        }else {
            return "success";
        }
    }

    /*对密码进行加密处理*/
    @Override
    public User save(User user) {
        //注册时进行MD5加密
        String password=md5Utils.getPassword(user.getUserName(),user.getUserPassword());
        user.setUserPassword(password);
        user.setUserStatue(0);
        BigDecimal money=new BigDecimal(0);
        user.setUserMoney(money);
        return userRepository.save(user);
    }

    /*通过id查找对象*/
    @Override
    public User findByUserId(Integer userId) {
        return userRepository.findById(userId).get();
    }

    /*邮箱激活后改变修改用户状态*/
    @Override
    public User update(User user) {
        return userRepository.saveAndFlush(user);
    }

    /*登录*/
    @Override
    public String login(String loginName,String password) {
        //登录时进行MD5加密
        List<User> all = userRepository.findAllByUserTellOrUserEmailOrUserName(loginName, loginName, loginName);
        if(all.size()==0){
            return "用户名不存在";
        }else if (all.size()==1) {
            String pass= md5Utils.getPassword(all.get(0).getUserName(), password);
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

    /*通过用户id查用户对象*/
    @Override
    public User findById(Integer userId) {
        return userRepository.findById(userId).get();
    }

    /*用户完善个人信息*/
    @Override
    public User updateUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    /*
     * 管理人员界面
     */

    /*用户信息展示*/
    @Override
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    /*重置用户密码*/
    @Override
    public void resetPassword(Integer userId) {
        User user = userRepository.findById(userId).get();
        user.setUserPassword("123456");
        userRepository.saveAndFlush(user);
    }

    /*修改用户状态*/
    @Override
    public void updateUserStatue(Integer userId) {
        User user = userRepository.findById(userId).get();
        user.setUserStatue(1);
        userRepository.saveAndFlush(user);
    }
}
