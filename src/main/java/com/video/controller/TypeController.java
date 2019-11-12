package com.video.controller;

import com.video.domain.Type;
import com.video.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Description:
 * @Company: NB
 * @Author: Li Jia
 * @Date: 2019/11/12
 * @Time: 15:24
 */
@Controller
public class TypeController {

    @Autowired
    private TypeService typeService;

    @RequestMapping("/findAllTypes")
    public List<Type> findAllTypes(){
        return typeService.findAllTypes();
    }

}
