package com.video.domain;

import lombok.Data;

import javax.persistence.*;

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

    @Column(name = "pk_episode_id")
    private Integer episodeId;//剧集id

    @Column(name = "idx_user_name")
    private String userName;//用户名

    @Column(name = "idx_user_pic")
    private String userPic;//用户头像

    @Column(name = "idx_comment_content")
    private String commentContent;//评论内容

    @Column(name = "idx_comment_statue")
    private Integer commentStatue;//评论状态

}
