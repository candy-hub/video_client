package com.video.service;

import com.video.domain.Collection;
import com.video.response.Pagination;

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
    public Pagination findCollection(Integer id, Integer page, Integer size);
    //删除个人收藏
    public String deleteCollection(Integer userId,Integer videoId);

    public List<Collection> findAllCollection(Integer userId);

    public String deleteAllCollection(Integer userId);
}
