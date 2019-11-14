package com.video.controller;

import com.video.domain.Video;
import com.video.service.VideoService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
    public String addVideo(@RequestBody Video video) throws IOException {
        return videoService.addVideo(video);
    }

    //封面图片上传
    @RequestMapping("/uploadVideoPic")
    public String uploadVideoPic(@RequestParam("file") MultipartFile file){
        return videoService.uploadPic(file);
    }

    //删除视频
    @RequestMapping("/deleteVedio/{id}")
    public String deleteVideo(@PathVariable("id") Integer id) throws IOException {
        return videoService.deleteVideo(id);
    }

    //下载视频
    @RequestMapping("/download")
    public String downloadVideo(@RequestBody Video video){
        return videoService.download(video);
    }

    //导入es库
    @RequestMapping("/import")
    public String im() throws IOException {
        return videoService.im();
    }

    //模糊查询
    @RequestMapping("/search/{searchName}")
    public List<Map> search(@PathVariable String searchName) throws IOException {
        return videoService.search(searchName);
    }


}
