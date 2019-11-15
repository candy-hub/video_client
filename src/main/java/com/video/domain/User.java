package com.video.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "video_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_user_id")
    private Integer userId;//用户id

    @Column(name = "uk_user_name")
    private String userName;//用户名

    @Column(name = "uk_user_email")
    private String userEmail;//用户邮箱

    @Column(name = "uk_user_tell")
    private String userTell;//用户电话

    @Column(name = "idx_user_password")
    private String userPassword;//用户密码

    @Column(name = "idx_user_money")
    private BigDecimal userMoney;//用户账户余额

    @Column(name = "idx_user_pic")
    private String userPic;//用户头像

    @Column(name = "idx_user_realname")
    private String userRealname;//用户真实姓名

    @Column(name = "idx_user_hobby")
    private String userHobby;//用户爱好

    @Column(name = "idx_user_statue")
    private Integer userStatue;//用户状态

    @Column(name = "idx_user_uptime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date userUptime;//用户注册时间

    @Column(name = "idx_user_recharge")
    private String userRechargeOrderNumber;//用户充值账户订单编号

    @Column(name = "idx_user_recharge_vip")
    private String userRechargeVipOrderNumber;//用户充值会员订单编号
    
}
