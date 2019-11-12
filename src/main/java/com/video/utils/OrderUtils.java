package com.video.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class OrderUtils {

    public String getOrder(){
        String order=randomOrder();
        return order;
    }
    public static String randomOrder(){
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<18;i++){
            int num=random.nextInt(9);
            sb.append(num);
        }
        return sb.toString();
    }
}
