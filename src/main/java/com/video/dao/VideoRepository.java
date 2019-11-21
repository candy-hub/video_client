package com.video.dao;


import com.video.domain.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description:
 * @Company: NB
 * @Author: Li Jia
 * @Date: 2019/11/11
 * @Time: 11:49
 */
public interface VideoRepository extends JpaRepository<Video,Integer> {
    Page<Video>  findAllByUserId(Integer userId, Pageable pageable);

    List<Video> findAllByUserId(Integer userId);

    List<Video> findAllByTypeId(Integer typeId);

    List<Video> findAllByOrderByVideoLikeDesc();

    List<Video> findAllByTypeIdOrderByTypeIdDesc(Integer typeId);

}
