package com.video.service;

import com.video.domain.Collection;

import java.util.List;

/**
 * @Description:
 * @Company: NB
 * @Author: Li Jia
 * @Date: 2019/11/16
 * @Time: 10:03
 */
public interface CollectionService {
    //查询个人收藏
    public List<Collection> findCollection(Integer id);
    //删除个人收藏
    public String deleteCollection(Integer userId,Integer videoId);
}
