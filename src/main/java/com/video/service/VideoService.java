package com.video.service;


import com.video.domain.Video;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description:
 * @Company: NB
 * @Author: Li Jia
 * @Date: 2019/11/11
 * @Time: 14:52
 */
public interface VideoService {

    //上传视频
    public String upload(MultipartFile file);
    //添加视频
    public String addVideo(Video video);
    //上传封面
    public String uploadPic(MultipartFile file);
    //删除视频
    public String deleteVideo(Integer id);
    //视频下载
    public String download(Video video);

}
