package com.video.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_communication")   //用户之间交流表
@Data
public class Communication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_communication_id")
    private Integer id;

    @Column(name = "idx_user_id")
    private Integer userId; //发消息用户id，与用户表关联  此表用于存储所有跟用户进行私聊的用户信息，还需信息表，存储他们之间的消息交互

    @Column(name = "idx_user_name")
    private Integer userName; //发消息用户姓名

  /*  @Column(name = "idx_user_pic")
    private String userPic;//用户头像*/

    @Column(name = "idx_receive_id")
    private Integer userRid;  //接收消息的用户id

    @Column(name = "idx_receive_name")
    private Integer userRname; //接收消息的用户姓名

   /* @Column(name = "idx_receive_pic")
    private String userRpic;//用户头像

    @Column(name = "idx_user_message")
    private String message; //发送的消息*/

    @Column(name = "idx_communication_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date msgTime;//消息发送时间


}
