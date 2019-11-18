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

    @RequestMapping("/saveUserMsg")
    public Communication save(@RequestParam Communication communication){
        return communicationService.save(communication);
    }

    @RequestMapping("/findAllUserMsg/{userId}/{userRid}")
    public List<Communication> findAll(@PathVariable("userId") Integer userId, @PathVariable("userRid") Integer userRid){
        return communicationService.findAll(userId,userRid);
    }

}
