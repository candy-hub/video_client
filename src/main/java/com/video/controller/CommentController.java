package com.video.controller;

import com.video.domain.Comment;
import com.video.service.CommentService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class CommentController {

    @Resource
    private CommentService commentService;

    /*查找所有*/
    @RequestMapping("/findAllComment")
    public List<Comment> findAllComment(){
        return commentService.findAll();
    }

    /*存库*/
    @RequestMapping("/saveComment")
    public Comment save(@RequestBody Comment comment){
        return commentService.save(comment);
    }

    /*用户本人删除*/
    @RequestMapping("/deleteComment/{commentId}")
    public String delete(@PathVariable("commentId") int commentId){
        return commentService.delete(commentId);
    }

    /*举报*/
    @RequestMapping("/updateComment/{commentId}")
    public Comment update(@PathVariable("commentId") int commentId){
        return commentService.update(commentId);
    }

}
