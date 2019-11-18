package com.video.service;

import com.video.domain.Communication;

import java.util.List;

public interface CommunicationService {
    Communication save(Communication communication);

    List<Communication> findAll(Integer userId, Integer userRid);
}
