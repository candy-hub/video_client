package com.video.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.MultipartConfigElement;

/**
 * @Description:
 * @Company: NB
 * @Author: Li Jia
 * @Date: 2019/11/8
 * @Time: 15:24
 */
@Getter
@Setter
@Component
@Configuration
public class ConstantConfig {
    @Value("${lximage.endpoint}")
    private   String LXIMAGE_END_POINT;
    @Value("${lximage.keyid}")
    private  String LXIMAGE_ACCESS_KEY_ID;
    @Value("${lximage.keysecret}")
    private  String LXIMAGE_ACCESS_KEY_SECRET;
    @Value("${lximage.filePath}")
    private  String LXIMAGE_FILE_PATH;
    @Value("${lximage.bucketname}")
    private  String LXIMAGE_BUCKET_NAME;
    @Value("${lximage.url}")
    private String LXIMAGE_URL;

    @Value("${spring.server.MaxFileSize}")
    private String MaxFileSize;
    @Value("${spring.server.MaxRequestSize}")
    private String MaxRequestSize;

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //  单个数据大小
        factory.setMaxFileSize(MaxFileSize); // KB,MB
        /// 总上传数据大小
        factory.setMaxRequestSize(MaxRequestSize);
        return factory.createMultipartConfig();
    }

   }
