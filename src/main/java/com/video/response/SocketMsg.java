package com.video.response;

import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Company: NB
 * @Author: Li Jia
 * @Date: 2019/11/13
 * @Time: 15:16
 */
@Data
public class SocketMsg {
    private int type; //聊天类型0：群聊，1：单聊.
    private String fromUser;//发送者.
    private String toUser;//接受者.
    private List<String> msgs;//消息
    private String msg;//消息

}
