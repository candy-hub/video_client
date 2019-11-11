package com.video.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "video")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_video_id")
    private Integer videoId;//视频id

    @Column(name = "pk_type_id")
    private Integer typeId;//类别id

    @Column(name = "pk_user_id")
    private Integer userId;//用户id

    @Column(name = "idx_video_name")
    private String videoName;//视频名称

    @Column(name = "idx_video_pic")
    private String videoPic;//视频封面

    @Column(name = "idx_video_info")
    private String videoInfo;//视频介绍信息

    @Column(name = "idx_video_url")
    private String videoUrl;//视频路径

    @Column(name = "idx_video_username")
    private String videoUsername;//视频发布人

    @Column(name = "idx_video_like")
    private Integer videoLike;//视频点赞量

    @Column(name = "idx_video_favorite")
    private Integer videoFavorite;//视频收藏量

    @Column(name = "idx_video_reward")
    private BigDecimal videoReward;//视频打赏金额

    @Column(name = "idx_video_comment")
    private Integer videoComment;//视频评论量

    @Column(name = "idx_video_uptime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date videoUptime;//视频上传时间

    @Column(name = "idx_video_download")
    private Integer videoDownload;//视频下载量

    @Column(name = "idx_video_statue")
    private Integer videoStatue;//视频状态
}
