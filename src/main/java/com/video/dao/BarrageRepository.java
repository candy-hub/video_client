package com.video.dao;

import com.video.domain.Barrage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BarrageRepository extends JpaRepository<Barrage,Integer> {

//    List<Barrage> findAllByBarrageStatue(int barrageStatue);

}
