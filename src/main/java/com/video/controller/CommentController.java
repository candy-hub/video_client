package com.video.controller;

import com.video.domain.Barrage;
import com.video.domain.Comment;
import com.video.domain.Video;
import com.video.response.CommentVideo;
import com.video.response.Comments;
import com.video.response.Pagination;
import com.video.service.CommentService;
import org.springframework.web.bind.annotation.*;

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
    public Comment save(@RequestBody CommentVideo commentVideo){
        System.out.println(commentVideo);
        return commentService.save(commentVideo);
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

    @RequestMapping(value = "/findAllComment/{page}/{size}",method = RequestMethod.GET)   ////审核：分为被举报和正常
    public List<Comment> findAllComment(@PathVariable("page") Integer page,@PathVariable("size")Integer size){

        return commentService.findAllByComment(page,size);
    }
    /*举报  审核  修改*/
    @RequestMapping(value = "/updateCommentStatue/{commentId}",method = RequestMethod.GET)   //是否只被举报才审核
    public String updateStatue(@PathVariable("commentId") Integer commentId){
        commentService.updateStatue(commentId);
        return "修改成功";
    }

}
