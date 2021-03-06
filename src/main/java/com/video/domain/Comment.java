package com.video.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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

    @Column(name = "idx_user_id")
    private Integer userId;//评论用户id

    @Column(name = "idx_user_name")
    private String userName;//评论用户名

    @Column(name = "idx_user_pic")
    private String userPic;//评论用户头像

    @Column(name = "idx_respondent_aid")
    private Integer respondentId;//被评论人id

    @Column(name = "idx_respondent_name")
    private String respondentName;//被评论人用户名

    @Column(name = "idx_comment_content")
    private String commentContent;//评论内容

    @Column(name = "idx_comment_statue")
    private Integer commentStatue;//评论状态 0:正常  1：被举报   2：禁止显示（小黑屋）

    @Column(name = "idx_comment_rid")
    private Integer commentRid;//0：评论的是剧集   其他数字与commentId匹配，1：该评论是commentId==1 的评论

    @Column(name = "idx_comment_lid")
    private Integer commentLid;//0：评论的级别  0：评论的是文章，1：评论第一级

    @Column(name = "idx_comment_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  //入参格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")  //出参格式
    private Date commentTime;  //评论时间

    @Column(name = "idx_comment_count")
    private Integer commentCount;  //点赞数
}
