package com.video.dao;

import com.video.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Integer> {

    List<Comment> findAllByCommentStatue(int commentStatue);

    Page<Comment> findAllByCommentId(Integer commentId, Pageable pageable);  //从楼查询
   //Page<Comment> findAllByCid(Pageable pageable,Integer commentId);

    Page<Comment> findAllByCommentRid(Integer commentRid, Pageable pageable);  //主楼查询，固定rid=0

}
