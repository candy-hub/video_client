package com.video.service;

import com.video.domain.Admin;
import com.video.response.LoginResponse;
import com.video.response.SuccessAdmin;

/**
 * @author 乔乐钰
 * @date 2019/11/21 10:50
 */
public interface AdminService {
    SuccessAdmin login(LoginResponse loginResponse);//登录

    Admin findAdminByAName(String aName);//通过登录名查用户

    Admin findByAId(Integer aId);//通过用户id查用户对象
}
