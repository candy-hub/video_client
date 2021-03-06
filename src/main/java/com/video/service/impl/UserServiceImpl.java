package com.video.service.impl;


import com.video.dao.RecordRepository;
import com.video.dao.UserRepository;
import com.video.domain.Record;
import com.video.domain.User;
import com.video.response.LoginResponse;
import com.video.service.UserService;
import com.video.utils.Md5Utils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserRepository userRepository;

    @Resource
    Md5Utils md5Utils;

    @Resource
    RecordRepository recordRepository;

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
        user.setUserUptime(new Date());
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
    public String login(LoginResponse loginResponse) {
        //登录时进行MD5加密
        List<User> all = userRepository.findAllByUserTellOrUserEmailOrUserName(loginResponse.getLoginName(), loginResponse.getLoginName(), loginResponse.getLoginName());
        if(all.size()==0){
            return "用户名不存在";
        }else if (all.size()==1) {
            String pass= md5Utils.getPassword(all.get(0).getUserName(), loginResponse.getPassword());
            if (all.get(0).getUserStatue()==1||all.get(0).getUserStatue()==2){
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

    /*通过登录名查用户*/
    @Override
    public User findUserByLoginName(String loginName) {
        List<User> all = userRepository.findAllByUserTellOrUserEmailOrUserName(loginName, loginName, loginName);
        return all.get(0);
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

    /*用户修改密码*/
    @Override
    public String updatePassword(User user) {
        User user1 = userRepository.findById(user.getUserId()).get();
        String pass=md5Utils.getPassword(user.getUserName(),user.getUserPassword());
        user1.setUserPassword(pass);
        userRepository.save(user1);
        return "success";
    }

    @Override
    public void userRecharge(User user) {
        userRepository.findById(user.getUserId());
    }

    @Override
    public User findAllByUserRechargeOrderNumber(String userRechargeOrderNumber) {
        return userRepository.findAllByUserRechargeOrderNumber(userRechargeOrderNumber);
    }

    @Override
    public User findAllByUserRechargeVipOrderNumber(String userRechargeVipOrderNumber) {
        return userRepository.findAllByUserRechargeVipOrderNumber(userRechargeVipOrderNumber);
    }

    /*用户历史记录*/
    @Override
    public List<Record> findRecordByUserIdAndVideoId(Integer userId, Integer videoId) {
        return recordRepository.findAllByUserIdAndVideoId(userId,videoId);
    }

    @Override
    public Record ChangeRecord(Record record) {
        return recordRepository.save(record);
    }

    @Override
    public Record findAllRecord(Integer recordId) {
        return recordRepository.findById(recordId).get();
    }

    @Override
    public List<Record> findUserAllRecord(Integer userId) {
        return recordRepository.findAllByUserId(userId);
    }

    @Override
    public void delete(Integer recordId) {
        recordRepository.deleteById(recordId);
    }

    @Override
    public void batchDelete(List<Record> record) {
        recordRepository.deleteInBatch(record);
    }

    /*
     * 管理人员界面
     */

    /*用户信息展示*/
    @Override
    public List<User> findAllUser(Integer page, Integer size) {
        return userRepository.findAll();
    }


    /*修改用户状态*/
    @Override
    public void updateUserStatue(Integer userId) {
        User user = userRepository.findById(userId).get();
        user.setUserStatue(3);
        userRepository.saveAndFlush(user);
    }



    @Override
    public String checkCode(String code,String tel) {
        User user = userRepository.findAllByUserTell(tel);
        String userCode = user.getUserCode();
        if(code.equals(userCode)){
            return "1";
        }else{
            return "0";
        }

    }

    @Override
    public User findUserByTel(String tel) {
        User user = userRepository.findAllByUserTell(tel);
        return user;
    }
}
