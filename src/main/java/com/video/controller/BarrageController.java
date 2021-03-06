package com.video.controller;

import com.video.domain.Barrage;
import com.video.domain.Video;
import com.video.response.CommentVideo;
import com.video.service.BarrageService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class BarrageController {

    @Resource
    private BarrageService barrageService;


    @RequestMapping("/saveBarrage")
    public Barrage save(@RequestBody CommentVideo commentVideo){
        System.out.println(commentVideo);
        return barrageService.save(commentVideo);
    }

    /*
    * 后台管理
    */

    @RequestMapping("/findAllByStatue")   //审核
    public List<Barrage> findAllByStatue(){
        return barrageService.findAllByStatue();
    }


    @RequestMapping("/updateBarrage")
    public Barrage update(@RequestBody Barrage barrage){
        return barrageService.update(barrage);
    }

}
