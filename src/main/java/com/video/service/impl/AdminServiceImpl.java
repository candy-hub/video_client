package com.video.service.impl;

import com.video.dao.AdminRepository;
import com.video.domain.Admin;
import com.video.response.LoginResponse;
import com.video.response.SuccessAdmin;
import com.video.service.AdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 乔乐钰
 * @date 2019/11/21 11:00
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminRepository adminRepository;


    //登录
    @Override
    public SuccessAdmin login(LoginResponse loginResponse) {
        SuccessAdmin successAdmin=new SuccessAdmin();
        List<Admin> all = adminRepository.findByANameOrAEmailOrATell(loginResponse.getLoginName(), loginResponse.getLoginName(), loginResponse.getLoginName());
        if(all.size()==0){
            successAdmin.setSuccess("error");
            return successAdmin;
        }else if (all.size()==1) {
            if(all.get(0).getAPassword().equals(loginResponse.getPassword())){
                successAdmin.setSuccess("success");
                successAdmin.setAName(all.get(0).getAName());
                return successAdmin;
            }else{
                successAdmin.setSuccess("error");
                return successAdmin;
            }
        }else {
            successAdmin.setSuccess("error");
            return successAdmin;
        }
    }

    @Override
    public Admin findAdminByAName(String aName) {
        return null;
    }

    @Override
    public Admin findByAId(Integer aId) {
        return null;
    }
}
