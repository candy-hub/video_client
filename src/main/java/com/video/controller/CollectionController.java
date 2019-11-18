package com.video.controller;

import com.video.domain.Collection;
import com.video.response.Pagination;
import com.video.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description:
 * @Company: NB
 * @Author: Li Jia
 * @Date: 2019/11/16
 * @Time: 10:00
 */
@RestController
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    //查询个人收藏
    @RequestMapping("/findCollection/{userId}/{page}/{size}")
    public Pagination findCollection(@PathVariable("userId")Integer id, @PathVariable("page") Integer page, @PathVariable("size") Integer size){
        return collectionService.findCollection(id,page,size);
    }

    //删除个人收藏
    @RequestMapping("/deleteCollection/{userId}/{videoId}")
    public String deleteCollection(@PathVariable("userId")Integer userId,@PathVariable("videoId")Integer videoId){
        return collectionService.deleteCollection(userId,videoId);
    }
}
