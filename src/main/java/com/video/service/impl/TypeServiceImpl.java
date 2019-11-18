package com.video.service.impl;

import com.video.dao.TypeRepository;
import com.video.domain.Type;
import com.video.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Company: NB
 * @Author: Li Jia
 * @Date: 2019/11/12
 * @Time: 15:29
 */
@Service
public class TypeServiceImpl implements TypeService{

    @Autowired
    private TypeRepository typeRepository;

    @Override
    public List<Type> findAllTypes() {
        List<Type> list = typeRepository.findAll();
        return list;
    }

    @Override
    public String findTypeById(Integer id) {
        Type type = typeRepository.findById(id).get();
        String typeName = type.getTypeName();
        return typeName;
    }
}
