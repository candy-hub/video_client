package com.video.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "video_episode")
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_episode_id")
    private Integer episodeId;//剧集id

    @Column(name = "pk_video_id")
    private Integer videoId;//视频id

    @Column(name = "pk_user_id")
    private Integer userId;//用户id

    @Column(name = "idx_episode_username")
    private String episodeUsername;//视频发布人

    @Column(name = "idx_episode_name")
    private String episodeName;//剧集名称

    @Column(name = "idx_episode_pic")
    private String episodePic;//剧集封面

    @Column(name = "idx_episode_info")
    private String episodeInfo;//剧集介绍信息

    @Column(name = "idx_episode_url")
    private String episodeUrl;//剧集路径

    @Column(name = "idx_episode_like")
    private Integer videoLike;//剧集点赞量

    @Column(name = "idx_episode_favorite")
    private Integer videoFavorite;//剧集收藏量

    @Column(name = "idx_episode_reward")
    private BigDecimal videoReward;//剧集打赏金额

    @Column(name = "idx_episode_comment")
    private Integer videoComment;//剧集评论量

    @Column(name = "idx_episode_uptime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date videoUptime;//剧集上新时间

    @Column(name = "idx_episode_download")
    private Integer videoDownload;//剧集下载量

    @Column(name = "idx_episode_statue")
    private Integer videoStatue;//剧集状态


}
