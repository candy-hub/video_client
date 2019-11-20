package com.video.service.impl;

//import com.video.dao.CommentDao;
import com.video.dao.CommentRepository;
import com.video.domain.Comment;
import com.video.domain.Video;
import com.video.response.Comments;
import com.video.response.Pagination;
import com.video.service.CommentService;
import com.video.utils.SpringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentRepository commentRepository;

    private RedisTemplate redisTemplate = SpringUtils.getBean("redisTemplates");



    /*主楼*/
    @Override
    public Comments selectAll(int videoId, int page, int size) {
        Comments comments=new Comments();
        int commentRid=0;
        Pageable pages=PageRequest.of(page-1,size);
        List<Pagination> list=new ArrayList<>();
        Pagination pag=new Pagination();
        Page<Comment> all = commentRepository.findAllByCommentRidAndVideoId(commentRid,videoId, pages);
        pag.setList(all.getContent());
        pag.setTotal(all.getTotalElements());
        comments.setCom(pag);

        for (Comment comment:all.getContent()){
            Pageable p=PageRequest.of(0,3);
            Pagination pagination=new Pagination();
            Page<Comment> allByCid = commentRepository.findAllByCommentRidAndVideoId(comment.getCommentId(), videoId,p);
            pagination.setList(allByCid.getContent());
            pagination.setTotal(allByCid.getTotalElements());  //总条数
//            过期时间
//            redisTemplate.opsForHash().put(comment.getVideoId()+"视频评论",comment.getCommentId()+"下附属评论",pagination);
            list.add(pagination);
        }

        comments.setComment(list);
        return comments;
    }

    /*从楼分页  页面显示时记得判断状态码*/
    @Override
    public List<Comment> findByPage(Integer commentId,Integer videoId) {
        List<Comment> all = commentRepository.findAllByCommentRidAndVideoId(commentId, videoId);
        all.sort(new Comparator<Comment>() {
            @Override
            public int compare(Comment o1, Comment o2) {
                return o2.getCommentId()-o1.getCommentId();
            }
        });
        return all;
    }

    @Override
    public Comment save(Comment comment,Video video) {
        comment.setCommentTime(new Date());
        comment.setCommentStatue(0);
        if (comment!=null){
            Comment save = commentRepository.save(comment);

            //存最新动态
            video.setVideoUptime(new Date());
            redisTemplate.opsForHash().put(video.getTypeId()+"动态",video.getVideoId()+"视频",video);
            return save;
//            存最新动态

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

   /* @Override
    public Comment update(int commentId) {
        Optional<Comment> byId = commentRepository.findById(commentId);
        if (byId.isPresent()){
            Comment comment = byId.get();
            comment.setCommentStatue(1);  //被举报则状态码为1
            return commentRepository.save(comment);
        }else {
            return null;
        }
    }*/

    @Override
    public List<Comment> findAllByStatue(int commentStatue) {
        return commentRepository.findAllByCommentStatue(commentStatue);
    }



   /* @Override
    public Comment update(Comment comment) {
        return null;
    }*/

    @Override
    public Comment updateStatue(Comment comment) {
        return commentRepository.save(comment);
    }
}
