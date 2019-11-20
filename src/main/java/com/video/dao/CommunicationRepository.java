package com.video.dao;

import com.video.domain.Communication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunicationRepository extends JpaRepository<Communication,Integer> {

    List<Communication> findAllByUserId(Integer userId); //查找与该用户进行过信息交互的所有用户

    List<Communication> findAllByUserIdAndUserRid(Integer userId, Integer userRid);  //查找与该用户进行过信息交互的某个用户。发送消息时存储，调用此方法时清空redis

}
