package com.video.dao;


import com.video.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record,Integer> {

    List<Record> findAllByUserId(Integer userId);

}
