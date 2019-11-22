package com.video.dao;

import com.video.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Integer> {



    Page<Comment> findAllByCommentIdAndVideoId(Integer commentId,Integer videoId, Pageable pageable);  //从楼查询
   //Page<Comment> findAllByCid(Pageable pageable,Integer commentId);

    List<Comment> findAllByCommentRidAndVideoId(Integer commentId,Integer videoId);

    Page<Comment> findAllByCommentRidAndVideoId(Integer commentRid,Integer videoId, Pageable pageable);  //主楼查询，固定rid=0

    //后台评论管理
    List<Comment> findAll();

    List<Comment> findAllByCommentId(Integer commentId);
}
