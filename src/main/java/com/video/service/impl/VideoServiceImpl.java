package com.video.service.impl;

import com.video.dao.VideoRepository;
import com.video.domain.Video;
import com.video.service.VideoService;
import com.video.utils.OssDownloadUtils;
import com.video.utils.OssUploadUtils;
import com.video.utils.QiniuUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;


/**
 * @Description:
 * @Company: NB
 * @Author: Li Jia
 * @Date: 2019/11/11
 * @Time: 14:54
 */
@Service
public class VideoServiceImpl implements VideoService{

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private OssUploadUtils ossUploadUtils;

    @Autowired
    private QiniuUploadUtils qiniuUploadUtils;

    @Autowired
    private OssDownloadUtils ossDownloadUtils;

    private static String objectName;


    @Override
    public String upload(MultipartFile file) {
        objectName= file.getOriginalFilename();

        //System.out.println(filename);
        try {
            if (file!=null) {
                if (!"".equals(objectName.trim())) {
                    File newFile = new File(objectName);
                    FileOutputStream os = new FileOutputStream(newFile);
                    os.write(file.getBytes());
                    os.close();
                    file.transferTo(newFile);

                    // 上传到OSS
                    String s = ossUploadUtils.upLoad(newFile);
                    //判断是否上传成功
                    if(s.equals(0)){
                        //失败返回0
                        return "0";
                    }else {
                        System.out.println("外链为===="+s);
                        return s;
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public String addVideo(Video video) {
        //System.out.println(objectName);
        video.setVideoLike(0);
        video.setVideoFavorite(0);
        video.setVideoComment(0);
        BigDecimal bigDecimal=new BigDecimal(0.00);
        video.setVideoReward(bigDecimal);
        video.setVideoDownload(0);
        video.setVideoStatue(0);
        video.setVideoObjectName(objectName);
        video.setVideoUptime(new Date());
        videoRepository.save(video);
        return "1";
    }

    @Override
    public String uploadPic(MultipartFile file) {
        if (Objects.isNull(file) || file.isEmpty()) {
            return "fail";
        }else {
            String path = qiniuUploadUtils.upload(file);
            return path;
        }
     }

    @Override
    public String deleteVideo(Integer id) {
        videoRepository.deleteById(id);
        return "1";
    }

    @Override
    public String download(Video video) {
        String name = video.getVideoObjectName();
        try {
            String s = ossDownloadUtils.downLoad(name);
            if(s.equals(0)){
                //失败返回0
                return "0";
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return "1";
    }
}
