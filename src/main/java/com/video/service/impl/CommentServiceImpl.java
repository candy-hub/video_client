package com.video.service.impl;

import com.video.dao.CommentRepository;
import com.video.domain.Barrage;
import com.video.domain.Comment;
import com.video.service.CommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentRepository commentRepository;

    @Override
    public List<Comment> findAll() {

        List<Comment> all = commentRepository.findAll();

        List<Comment> list=new ArrayList<>();

        if (all!=null){
            for (Comment comment:all){
                if (comment.getCommentRid()==0){
                    list.add(comment);
                    System.out.println(comment);
                    for (Comment com:all) {
                        if (comment.getCommentId() == com.getCommentRid() && comment.getCommentId()!=com.getCommentId()) {
                            System.out.println(com);
                            list.add(com);
                        }
                    }
                }
            }
            return list;
        }else {
            return null;
        }
    }

    @Override
    public Comment save(Comment comment) {
        comment.setCommentTime(new Date());
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

    @Override
    public List<Comment> findAllByStatue(int commentStatue) {
        return commentRepository.findAllByCommentStatue(commentStatue);
    }

    @Override
    public Comment updateStatue(Comment comment) {
        return commentRepository.save(comment);
    }
}
