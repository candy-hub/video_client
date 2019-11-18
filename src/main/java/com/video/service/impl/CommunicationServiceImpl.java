package com.video.service.impl;

import com.video.dao.CommunicationRepository;
import com.video.domain.Communication;
import com.video.service.CommunicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommunicationServiceImpl implements CommunicationService {

    @Autowired
    private CommunicationRepository communicationRepository;


    @Override
    public Communication save(Communication communication) {
        communication.setMsgTime(new Date());
        return communicationRepository.save(communication);
    }

    @Override
    public List<Communication> findAll(Integer userId, Integer userRid) {

        return communicationRepository.findAllByUserIdAndUserRid(userId,userRid);
    }
}
