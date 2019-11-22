package com.video.controller;

import com.video.domain.Communication;
import com.video.response.UserMsg;
import com.video.service.CommunicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

@RestController
public class CommunicationController {

    @Autowired
    private CommunicationService communicationService;

    @Resource(name = "redisTemplates")
    private RedisTemplate redisTemplate;

//    存储交互信息
    /*@RequestMapping("/sendMessage")
    public Communication save(@RequestParam Communication communication){
        redisTemplate.opsForHash().put(communication.getUserRid()+"接收消息",communication.getUserId()+"发送消息",communication);
        return communicationService.save(communication);
    }*/

    //私聊时把对方用户信息存储下来,过期时间为1小时
    @RequestMapping("/sendMessage")
    public void findUserBy(@RequestBody Communication communication){
        communication.setMsgTime(new Date());
        redisTemplate.opsForHash().put(communication.getUserRid()+"接收消息",communication.getUserId()+"发送消息",communication);
    }

    /*//查找与该用户进行过信息交互的某个用户
    @RequestMapping("/findAllUserMsg/{userId}/{userRid}")
    public List<Communication> findAll(@PathVariable("userId") Integer userId, @PathVariable("userRid") Integer userRid){
        return communicationService.findAll(userId,userRid);
    }*/

    //查找与该用户进行过信息交互的某个用户
    @RequestMapping("/findMsg/{userId}")
    public List<Communication> findAll(@PathVariable("userId") Integer userId) {
        Map entries = redisTemplate.opsForHash().entries(userId + "接收消息");
        List<Communication> communications = null;
        if (entries.size() > 0) {
            /*把map中值遍历存入集合中*/
            java.util.Collection<Object> values = entries.values();
            Iterator<Object> iterator = values.iterator();
            while (iterator.hasNext()) {
                communications.add((Communication) iterator.next());
            }
            communications.sort(new Comparator<Communication>() {
                @Override
                public int compare(Communication o1, Communication o2) {
                    return o2.getMsgTime().compareTo(o1.getMsgTime());
                }
            });
            return communications;
        }
        return null;
    }

    @RequestMapping("/deleteMsg/{userId}/{userRid}")
    public void deleteMsg(@PathVariable("userId") Integer userId,@PathVariable("userRid") Integer userRid){
        redisTemplate.opsForHash().delete(userId+"接收消息",userRid+"发送消息");
    }
}
