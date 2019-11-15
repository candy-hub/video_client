package com.video.service;

import com.video.domain.Barrage;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BarrageService {
    Barrage save(Barrage barrage);

    Barrage update(Barrage barrage);

    List<Barrage> findAllByStatue();
}
