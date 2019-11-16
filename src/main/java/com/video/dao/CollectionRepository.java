package com.video.dao;

import com.video.domain.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description:
 * @Company: NB
 * @Author: Li Jia
 * @Date: 2019/11/15
 * @Time: 11:39
 */
public interface CollectionRepository extends JpaRepository<Collection,Integer> {
    List<Collection> findAllByUserIdAndVideoId(Integer userId,Integer videoId);
}
