package com.video.service.impl;

import com.video.dao.CommentDao;
import com.video.dao.CommentRepository;
import com.video.domain.Comment;
import com.video.response.Comments;
import com.video.response.Pagination;
import com.video.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentRepository commentRepository;

    @Resource
    private CommentDao commentDao;

    @Resource
    private RedisTemplate redisTemplate;

   /* @Override
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
    }*/

    /*主楼*/
    @Override
    public Comments selectAll(int videoId) {
        Comments comments=new Comments();
        int commentRid=0;
        Pageable pages=PageRequest.of(0,10);
        List<Pagination> list=new ArrayList<>();
        Pagination pag=new Pagination();
        Page<Comment> all = commentRepository.findAllByCommentRidAndVideoId(commentRid,videoId, pages);
        pag.setList(all.getContent());
        pag.setTotal(all.getTotalElements());
        comments.setCom(pag);

        for (Comment comment:all.getContent()){
            Pagination pagination=new Pagination();
            Page<Comment> allByCid = commentRepository.findAllByCommentRidAndVideoId(comment.getCommentId(), videoId,pages);
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
    public Pagination<Comment> findByPage(Integer commentId,int videoId, int page, int size) {
        Pageable pages=PageRequest.of(page-1,size);
        Pagination pagination=new Pagination();
        Page<Comment> allByCid = commentRepository.findAllByCommentIdAndVideoId(commentId,videoId, pages);
        pagination.setList(allByCid.getContent());
        pagination.setTotal(allByCid.getTotalElements());
        return pagination;
    }

    @Override
    public List<Comment> findAll() {
        return commentDao.findAllByRid();
    }

    @Override
    public List<Comment> findAll2() {
        return commentDao.findAllByRid2();
    }

    @Override
    public Comment save(Comment comment) {
        comment.setCommentTime(new Date());
        comment.setCommentStatue(0);
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
