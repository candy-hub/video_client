package com.video.service;

import com.video.domain.User;
import com.video.response.LoginResponse;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {

    /*
     * 用户界面
     */

    String findByRegisterName(User user);//对输入的用户名，邮箱，手机号查重

    User save(User user);//保存用户信息

    User findByUserId(Integer userId);//通过id查找对象

    User update(User user );//邮箱激活后改变修改用户状态

    String login(LoginResponse loginResponse);//登录

    User findUserByLoginName(String loginName);//通过登录名查用户

    User findById(Integer userId);//通过用户id查用户对象

    User updateUser(User user);//用户完善个人信息

    //String updatePassword(String );

    void userRecharge(User user);//用户充值

    User findAllByUserRechargeOrderNumber(String userRechargeOrderNumber);//通过订单编号查对象

    /*
     * 管理人员界面
     */
    List<User> findAllUser();//用户信息展示

    void resetPassword(Integer userId);//重置用户密码

    void updateUserStatue(Integer userId);//修改用户的状态码

}
