package com.video.service.impl;

import com.video.dao.CommentRepository;
import com.video.domain.Comment;
import com.video.service.CommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentRepository commentRepository;

    @Override
    public List<Comment> findAll() {

        List<Comment> all = commentRepository.findAll();

        if (all!=null){
            return all;
        }else {
            return null;
        }
    }

    @Override
    public Comment save(Comment comment) {
        if (comment!=null){
            return commentRepository.save(comment);
        }else {
            return null;
        }
    }

    @Override
    public String delete(int commentId) {
        try{
            commentRepository.deleteById(commentId);
            return "success";
        }catch (Exception e){
            return "fail";
        }
    }

    @Override
    public Comment update(int commentId) {
        Optional<Comment> byId = commentRepository.findById(commentId);
        if (byId.isPresent()){
            Comment comment = byId.get();
            comment.setCommentStatue(1);  //被举报则状态码为1
            return commentRepository.save(comment);
        }else {
            return null;
        }
    }
}
