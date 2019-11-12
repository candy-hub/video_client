package com.video.controller;

import com.video.domain.Video;
import com.video.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description:
 * @Company: NB
 * @Author: Li Jia
 * @Date: 2019/11/11
 * @Time: 14:49
 */
@RestController
public class VideoController {

    @Autowired
    private VideoService videoService;

    //上传视频
    @RequestMapping("/uploadVideo")
    public String uploadVideo(@RequestParam("file") MultipartFile file){
        return videoService.upload(file);
    }

    //添加视频
    @RequestMapping("/addVideo")
    public String addVideo(@RequestBody Video video){
        return videoService.addVideo(video);
    }

    //封面图片上传
    @RequestMapping("/uploadVideoPic")
    public String uploadVideoPic(@RequestParam("file") MultipartFile file){
        return videoService.uploadPic(file);
    }

    //删除视频
    @RequestMapping("/deleteVedio/{id}")
    public String deleteVideo(@PathVariable("id") Integer id){
        return videoService.deleteVideo(id);
    }

    //下载视频
    @RequestMapping("/download")
    public String downloadVideo(@RequestBody Video video){
        return videoService.download(video);
    }


}
