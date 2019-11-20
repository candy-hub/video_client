package com.video.service;

import com.video.domain.Barrage;
import com.video.domain.Video;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BarrageService {
    Barrage save(Barrage barrage, Video video);

    Barrage update(Barrage barrage);

    List<Barrage> findAllByStatue();
}
