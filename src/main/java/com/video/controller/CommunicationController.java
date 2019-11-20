package com.video.controller;

import com.video.domain.Communication;
import com.video.service.CommunicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommunicationController {

    @Autowired
    private CommunicationService communicationService;

//    存储交互信息
    @RequestMapping("/saveUserMsg")
    public Communication save(@RequestParam Communication communication){
        return communicationService.save(communication);
    }

    //查找与该用户进行过信息交互的某个用户
    @RequestMapping("/findAllUserMsg/{userId}/{userRid}")
    public List<Communication> findAll(@PathVariable("userId") Integer userId, @PathVariable("userRid") Integer userRid){
        return communicationService.findAll(userId,userRid);
    }

}
