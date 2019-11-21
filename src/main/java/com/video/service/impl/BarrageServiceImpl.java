package com.video.service.impl;

import com.video.dao.BarrageRepository;
import com.video.domain.Barrage;
import com.video.domain.Video;
import com.video.response.CommentVideo;
import com.video.service.BarrageService;
import com.video.utils.SpringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BarrageServiceImpl implements BarrageService {

  /*  @Resource
    private RedisTemplate redisTemplate;*/

    private RedisTemplate redisTemplate = SpringUtils.getBean("redisTemplates");

    @Resource
    private BarrageRepository barrageRepository;

    @Override
    public Barrage save(CommentVideo commentVideo) {
        Barrage barrage=commentVideo.getBarrage();
        Video video=commentVideo.getVideo();
        Barrage save = barrageRepository.save(barrage);

        //存最新动态
        video.setVideoUptime(new Date());
        redisTemplate.opsForHash().put(video.getTypeId()+"动态",video.getVideoId()+"视频",video);

        Integer videoId = save.getVideoId();
        List<String> list=new ArrayList<>();
        Object o = redisTemplate.opsForValue().get("视频弹幕:" + videoId + "时间：" + save.getVideoTime());
        if (o!=null) {
            list = (List<String>) o;
        }
        list.add(save.getBarrageContent());
        redisTemplate.opsForValue().set("视频弹幕:"+videoId+"时间："+save.getVideoTime(),list);
        return save;
    }

    @Override
    public Barrage update(Barrage barrage) {
        Barrage save = barrageRepository.save(barrage);
        Integer videoId = save.getVideoId();
        List<String> list=new ArrayList<>();
        Integer videoTime = save.getVideoTime();

        Object o = redisTemplate.opsForHash().get(videoId + "视频弹幕", "时间：" + videoTime);
        if (o!=null) {
            list = (List<String>) o;
            for (String s:list) {
                if (save.getBarrageContent().equals(s)){
                    list.remove(s);
                }
            }
        }else {
            list.add(save.getBarrageContent());
        }
        redisTemplate.opsForHash().put(videoId + "视频弹幕", "时间：" + videoTime, list);
        return save;
    }

    @Override
    public List<Barrage> findAllByStatue() {
        return barrageRepository.findAll();
    }
}
