package com.video.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "video_type")
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_type_id")
    private Integer typeId;//类别id

    @Column(name = "uk_type_name")
    private String typeName;//类别名称

    @Column(name = "idx_type_info")
    private String typeInfo;//类别信息

}
