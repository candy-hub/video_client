package com.video.service.impl;

import com.video.dao.CollectionRepository;
import com.video.dao.VideoRepository;
import com.video.domain.Collection;
import com.video.domain.Video;
import com.video.response.Pagination;
import com.video.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Pagination findCollection(Integer id,Integer page,Integer size){
        Pageable pages= PageRequest.of(page-1,size);
        Page<Collection> all = collectionRepository.findAllByUserId(id, pages);
        Pagination res=new Pagination();
        res.setList(all.getContent());
        res.setTotal(all.getTotalElements());
        return res;
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
