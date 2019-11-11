package com.video.service.impl;


import com.video.dao.UserRepository;
import com.video.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserRepository userRepository;

}
