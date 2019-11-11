package com.video.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_collection")
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_collection_id")
    private Integer collectionId;//收藏id

    @Column(name = "pk_user_id")
    private Integer userId;//用户id

    @Column(name = "pk_episode_id")
    private Integer episodeId;//剧集id

    @Column(name = "pk_video_id")
    private Integer videoId;//视频id

    @Column(name = "pk_collection_info")
    private String collectionInfo;//收藏信息
}
