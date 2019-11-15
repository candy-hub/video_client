package com.video.controller;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.video.response.SocketMsg;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;


@ServerEndpoint(value = "/websoc/{fromId}")
@Component
public class UserWebSocketController {

    private int fromId;

    private Session session;

    //用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<UserWebSocketController> webSocketSet = new CopyOnWriteArraySet<UserWebSocketController>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    //用来记录sessionId和该session进行绑定
    private static Map<String, Session> map = new HashMap<String, Session>();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("fromId") int fromId) {
        Map<String,Object> message=new HashMap<String, Object>();
        this.session = session;
        this.fromId = fromId;
        /*session中是WebSocket建立连接存储的系列信息，其中包含建立连接时的频道号，属自动行为*/
        map.put(session.getId(), session);
        webSocketSet.add(this);//加入set中
        System.out.println("有新连接加入:" + fromId + ",当前在线人数为" + webSocketSet.size());
        //this.session.getAsyncRemote().sendText("恭喜" + nickname + "成功连接上WebSocket(其频道号：" + session.getId() + ")-->当前在线人数为：" + webSocketSet.size());
        message.put("type",0); //消息类型，0-连接成功，1-用户消息
        message.put("people",webSocketSet.size()); //在线人数
        message.put("name",fromId); //昵称
        message.put("videoId",fromId); //message.put("aisle",session.getId());频道号
        this.session.getAsyncRemote().sendText(new Gson().toJson(message));
    }

    /**
     * 连接关闭调用的方法    
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this); //从set中删除
        System.out.println("有一连接关闭！当前在线人数为" + webSocketSet.size());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("fromId") int fromId) {

//        message:视频当前播放时间   nickname：视频id

        System.out.println("来自客户端的消息-->" + fromId + ": " + message);

        //从客户端传过来的数据是json数据，所以这里使用jackson进行转换为SocketMsg对象，
        // 然后通过socketMsg的type进行判断是单聊还是群聊，进行相应的处理:
        ObjectMapper objectMapper = new ObjectMapper();
        SocketMsg socketMsg;


        try {
            socketMsg = objectMapper.readValue(message, SocketMsg.class);
            /*Object o = redisTemplate.opsForHash().get(videoId+"视频弹幕", "时间："+socketMsg.getMsg());
            List<String> msgList=(List<String>)o;*/
//            socketMsg.setMsg(msgList);
            if (socketMsg.getType() == 1) {
                //单聊.需要找到发送者和接受者.
                socketMsg.setFromUser(fromId+"");//发送者.
                Session fromSession = map.get(socketMsg.getFromUser());
                Session toSession = map.get(socketMsg.getToUser());
                //发送给接受者.
                if (toSession != null) {
                    //发送给发送者.
                    Map<String,Object> m=new HashMap<String, Object>();
                    m.put("type",1);
                    m.put("name",fromId);
                    m.put("msg",socketMsg.getMsgs());
                    fromSession.getAsyncRemote().sendText(new Gson().toJson(m));
                    toSession.getAsyncRemote().sendText(new Gson().toJson(m));
                } else {
                    //发送给发送者.
                    fromSession.getAsyncRemote().sendText("系统消息：对方不在线或者您输入的频道号不对");
                }
            } else {
                //群发消息
                //broadcast(nickname + ": " + socketMsg.getMsg());
            }

        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发生错误时调用   
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 群发自定义消息
     */
   /* public void broadcast(String message) {
        for (WebSocketService item : webSocketSet) {
            item.session.getAsyncRemote().sendText(message);//异步发送消息.
        }
    }*/

}
