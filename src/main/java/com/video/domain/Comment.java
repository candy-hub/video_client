package com.video.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "video_comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_comment_id")
    private Integer commentId;//评论id

    @Column(name = "pk_video_id")
    private Integer videoId;//视频id

   /* @Column(name = "pk_episode_id")
    private Integer episodeId;//剧集id*/

    @Column(name = "idx_user_name")
    private String userName;//用户名

    @Column(name = "idx_user_pic")
    private String userPic;//用户头像

    @Column(name = "idx_comment_content")
    private String commentContent;//评论内容

    @Column(name = "idx_comment_statue")
    private Integer commentStatue;//评论状态

    @Column(name = "idx_comment_rid")
    private Integer commentRid;//0：评论的是剧集   其他数字与commentId匹配，1：该评论是commentId==1 的评论

    @Column(name = "idx_comment_time")
    private Date commentTime;  //评论时间

    @Column(name = "idx_comment_count")
    private Integer commentCount;  //点赞数
}
