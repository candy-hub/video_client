package com.video.service.impl;

import com.video.dao.BarrageRepository;
import com.video.domain.Barrage;
import com.video.service.BarrageService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class BarrageServiceImpl implements BarrageService {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private BarrageRepository barrageRepository;

    @Override
    public Barrage save(Barrage barrage) {
        Barrage save = barrageRepository.save(barrage);
        Integer videoId = save.getVideoId();
        List<String> list=new ArrayList<>();
        /*
        * video表中无episodeId字段，故，无episodeId，则一定是video表对应弹幕
        * */
        Object o = redisTemplate.opsForHash().get(videoId + "视频弹幕", "时间：" + save.getVideoTime());
        if (o!=null) {
            list = (List<String>) o;
        }
        list.add(save.getBarrageContent());
        redisTemplate.opsForHash().put(videoId + "视频弹幕", "时间：" + save.getVideoTime(), list);
        return save;
    }
}
