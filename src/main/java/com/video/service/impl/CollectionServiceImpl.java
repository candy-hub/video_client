package com.video.service.impl;

import com.video.dao.CollectionRepository;
import com.video.dao.VideoRepository;
import com.video.domain.Collection;
import com.video.domain.Video;
import com.video.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Company: NB
 * @Author: Li Jia
 * @Date: 2019/11/16
 * @Time: 10:04
 */
@Service
public class CollectionServiceImpl implements CollectionService{

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Override
    public List<Collection> findCollection(Integer id) {
        List<Collection> list = collectionRepository.findAllByUserId(id);
        return list;
    }

    @Override
    public String deleteCollection(Integer userId,Integer videoId) {
        collectionRepository.deleteByUserIdAndVideoId(userId,videoId);
        Video video = videoRepository.findById(videoId).get();
        Integer videoFavorite = video.getVideoFavorite();
        int i = videoFavorite.intValue() - 1;
        Integer favorite = Integer.valueOf(i);
        video.setVideoFavorite(favorite);
        videoRepository.save(video);
        return "1";
    }
}
