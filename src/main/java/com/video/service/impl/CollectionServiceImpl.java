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
        List<Collection> list = collectionRepository.findAllByUserIdAndVideoId(userId, videoId);
        Integer collectionId = list.get(0).getCollectionId();
        collectionRepository.deleteById(collectionId);
        Video video = videoRepository.findById(videoId).get();
        Integer videoFavorite = video.getVideoFavorite();
        int i = videoFavorite.intValue() - 1;
        Integer favorite = Integer.valueOf(i);
        video.setVideoFavorite(favorite);
        videoRepository.save(video);
        return "1";
    }

    @Override
    public List<Collection> findAllCollection(Integer userId) {
        List<Collection> list = collectionRepository.findAllByUserId(userId);
        return list;
    }

    @Override
    public String deleteAllCollection(Integer userId) {
        List<Collection> list = collectionRepository.findAllByUserId(userId);
        for(int i=0;i<list.size();i++){
            collectionRepository.deleteById(list.get(i).getCollectionId());
            Video video = videoRepository.findById(list.get(i).getVideoId()).get();
            Integer videoFavorite = video.getVideoFavorite();
            int n = videoFavorite.intValue() - 1;
            Integer favorite = Integer.valueOf(n);
            video.setVideoFavorite(favorite);
            videoRepository.save(video);
        }
        return "1";
    }
}
