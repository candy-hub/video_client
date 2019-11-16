package com.video.service;


import com.video.domain.Comment;
import com.video.response.Comments;
import com.video.response.Pagination;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommentService {

    Comments selectAll();

    Pagination<Comment> findByPage(Integer commentId,int page,int size);

    List<Comment> findAll();

    List<Comment> findAll2();

//    List<Comment> findAll2(Integer commentId);

    Comment save(Comment comment);

    String delete(int commentId);

//    Comment update(int commentId);

    List<Comment> findAllByStatue(int commentStatue);

//    Comment update(Comment comment);

    Comment updateStatue(Comment comment);
}
