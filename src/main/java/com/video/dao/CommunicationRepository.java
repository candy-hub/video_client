package com.video.dao;

import com.video.domain.Communication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunicationRepository extends JpaRepository<Communication,Integer> {

    List<Communication> findAllByUserIdAndUserRid(Integer userId, Integer userRid);

}
