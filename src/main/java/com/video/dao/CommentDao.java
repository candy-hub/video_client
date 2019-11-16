package com.video.dao;

import com.video.domain.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentDao {

    List<Comment> findAllByRid();

    List<Comment> findAllByRid2();

    List<Comment> findAllByCid(Integer commentId);
}
