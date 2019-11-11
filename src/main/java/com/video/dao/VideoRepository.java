package com.video.dao;

import com.video.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description:
 * @Company: NB
 * @Author: Li Jia
 * @Date: 2019/11/11
 * @Time: 11:49
 */
public interface VideoRepository extends JpaRepository<Video,Integer> {

}
