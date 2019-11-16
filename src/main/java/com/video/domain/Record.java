package com.video.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "video_record")
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_record_id")
    private Integer recordId;//记录id

    @Column(name = "pk_user_id")
    private Integer userId;//用户id

    @Column(name = "pk_video_id")
    private Integer videoId;//视频id

    @Column(name = "idx_video_pic")
    private String videoPic;//视频封面

}
