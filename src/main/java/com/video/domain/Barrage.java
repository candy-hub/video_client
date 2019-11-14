package com.video.domain;

import lombok.Data;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Data
@Entity
@Table(name = "video_barrage")
public class Barrage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_barrage_id")
    private Integer barrageId;//弹幕id

    @Column(name = "pk_user_id")
    private Integer userId;//用户id

    @Column(name = "pk_video_id")
    private Integer videoId;//视频id

   /* @Column(name = "pk_episode_id")
    private Integer episodeId;//剧集id*/

    @Column(name = "idx_barrage_content")
    private String barrageContent;//弹幕内容

    @Column(name = "idx_barrage_statue")
    private Integer barrageStatue;//弹幕状态

    @Column(name = "idx_barrage_time")
    private Date barrageTime;//发表弹幕时的当前时间

    @Column(name = "idx_barrage_videotime")
    private Integer videoTime;//发表弹幕时的视频播放时间
}
