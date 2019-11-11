package com.video.service;

import com.video.domain.User;

public interface UserService {

    String findByRegisterName(User user);





    User save(User user);

    User findById(Integer id);



    String login(String loginName,String password);

    String updateUser(User user);

    User findByName(String userName);

    String updatePassword(User user);

    public User findByUId(int uId);

    User update(User users);
}
