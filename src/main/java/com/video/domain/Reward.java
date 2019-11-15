package com.video.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "video_reward")
public class Reward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_reward_id")
    private Integer rewardId;//打赏id

    @Column(name = "pk_user_id")
    private Integer userId;//用户id

    @Column(name = "pk_video_id")
    private Integer videoId;//视频id

   /* @Column(name = "pk_episode_id")
    private Integer episodeId;//剧集id*/

    @Column(name = "idx_reward_money")
    private BigDecimal rewardContent;//打赏金额

    @Column(name = "idx_reward_statue")
    private Integer rewardStatue;//打赏状态

}
