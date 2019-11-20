package com.video.service;


import com.video.domain.Collection;
import com.video.domain.Video;
import com.video.response.Pagination;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
    public String addVideo(Video video) throws IOException;
    //上传封面
    public String uploadPic(MultipartFile file);
    //删除视频
    public String deleteVideo(Integer id) throws IOException;
    //视频下载
    public String download(Video video);
    //导入es库
    public String im() throws IOException;
    //模糊查询
    public List<Map> search(String searchName) throws IOException;
    //收藏视频
    public String favorite(Integer userId,Integer videoId);
    //点赞视频
    public String like(Integer id);
    //根据id查视频
    public Video findVideoByVideoId(Integer id);
    //根据userId查询对应的上传视频
    public Pagination findVideoByUserId(Integer id, Integer page, Integer size);
    //根据userId查询
    public List<Video> findVideoById(Integer id);
}
