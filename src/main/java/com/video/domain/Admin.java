package com.video.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "a_id")
    private Integer aId;

    @Column(name = "a_name")
    private String aName;

    @Column(name = "a_password")
    private String aPassword;

    @Column(name = "a_tell")
    private String aTell;

    @Column(name = "create_time")
//    入参格式
    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    出参格式
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;//注册时间

    @Column(name = "a_statue")
    private Integer aStatue;//管理员的状态

    @Column(name = "a_email")
    private String aEmail;
}
