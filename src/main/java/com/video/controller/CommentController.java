package com.video.controller;

import com.video.domain.Barrage;
import com.video.domain.Comment;
import com.video.response.Comments;
import com.video.response.Pagination;
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

    /*查找所有rid==0*/
   /* @RequestMapping("/findAllComment")
    public List<Comment> findAllComment(){
        return commentService.findAll();
    }*/

    /*查找所有rid==0*/
    @RequestMapping("/findAllCom/{videoId}/{page}/{size}")
    public Comments findAllCom(@PathVariable int videoId,@PathVariable int page,@PathVariable int size){
        return commentService.selectAll(videoId,page,size);
    }

    /*查找所有rid!=0*/
    @RequestMapping("/searchComment/{commentId}/{videoId}")
    public List<Comment> searchComment(@PathVariable int commentId,@PathVariable int videoId){
        return commentService.findByPage(commentId,videoId);
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

   /* *//*修改*//*
    @RequestMapping("/updateComment")
    public Comment update(@RequestBody Comment comment){
        return commentService.update(comment);
    }*/

   /* *//*举报*//*
    @RequestMapping("/updateComment/{commentId}")
    public Comment updateStatue(@PathVariable("commentId") int commentId){
        return commentService.updateStatue(commentId);
    }*/

    /*
     * 后台管理
     */

    @RequestMapping("/findAllByStatue/{commentStatue}")   ////审核：分为被举报和正常
    public List<Comment> findAllByStatue(@PathVariable int commentStatue){

        return commentService.findAllByStatue(commentStatue);
    }
    /*举报  审核  修改*/
    @RequestMapping("/updateComment")   //是否只被举报才审核
    public Comment updateStatue(@RequestBody Comment comment){
        return commentService.updateStatue(comment);
    }


}
