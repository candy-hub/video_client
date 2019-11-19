package com.video.dao;


import com.video.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record,Integer> {

    List<Record> findAllByUserId(Integer userId);

    List<Record> findAllByUserIdAndVideoId(Integer userId,Integer videoId);
}
