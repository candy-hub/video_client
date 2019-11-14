package com.video.service;


import com.video.domain.Comment;

import java.util.List;

public interface CommentService {


    List<Comment> findAll();

    Comment save(Comment comment);

    String delete(int commentId);

//    Comment update(int commentId);

    List<Comment> findAllByStatue(int commentStatue);

//    Comment update(Comment comment);

    Comment updateStatue(Comment comment);
}
