package com.video.service;

import com.video.domain.Type;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Description:
 * @Company: NB
 * @Author: Li Jia
 * @Date: 2019/11/12
 * @Time: 15:28
 */
public interface TypeService {

    public List<Type> findAllTypes();

    public String findTypeById(Integer id);

    boolean batchImport(String fileName, MultipartFile file)throws Exception;
}
