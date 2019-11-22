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

    @Column(name = "idx_type_like")
    private Integer typeLike;//类别总点赞量

    @Column(name = "idx_type_favorite")
    private Integer typeFavorite;//类别总收藏量

    @Column(name = "idx_type_download")
    private Integer typeDownload;//类别总下载量

    @Column(name = "idx_type_comment")
    private Integer typeComment;//类别总评论量



}
