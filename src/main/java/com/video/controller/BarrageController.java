package com.video.controller;

import com.video.domain.Barrage;
import com.video.service.BarrageService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class BarrageController {

    @Resource
    private BarrageService barrageService;


    @RequestMapping("/saveBarrage")
    public Barrage save(@RequestBody Barrage barrage){
        return barrageService.save(barrage);
    }

}
