package com.video.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "user_reply")
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_reply_id")
    private Integer replyId;//回复id

    @Column(name = "pk_comment_id")
    private Integer commentId;//评论id

    @Column(name = "idx_reply_username")
    private String replyUsername;//回复人

    @Column(name = "idx_reply_userpic")
    private String replyUserpic;//回复人头像

    @Column(name = "idx_reply_content")
    private String replyContent;//回复内容

    @Column(name = "idx_user_pic")
    private String userPic;//被回复人头像

    @Column(name = "idx_user_username")
    private String userUsername;//被回复人姓名

    @Column(name = "idx_reply_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date replyTime;//回复时间

    @Column(name = "idx_reply_statue")
    private Integer replyStatue;//回复状态
}
