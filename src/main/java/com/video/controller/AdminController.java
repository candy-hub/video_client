package com.video.controller;

import com.video.response.LoginResponse;
import com.video.response.SuccessAdmin;
import com.video.service.AdminService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 乔乐钰
 * @date 2019/11/21 11:17
 */
@RestController
public class AdminController {


    @Resource
    private AdminService adminService;

    @RequestMapping(value = "/adminLogin",method = RequestMethod.POST)
    public SuccessAdmin adminLogin(@RequestBody LoginResponse loginResponse){
        System.out.println(loginResponse);
        return adminService.login(loginResponse);
    }
}
