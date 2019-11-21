package com.video.service;

import com.video.domain.Barrage;
import com.video.domain.Video;
import com.video.response.CommentVideo;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BarrageService {
    Barrage save(CommentVideo commentVideo);

    Barrage update(Barrage barrage);

    List<Barrage> findAllByStatue();
}
