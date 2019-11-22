package com.video.controller;



import com.video.dao.CommentRepository;
import com.video.dao.UserRepository;
import com.video.dao.VideoRepository;
import com.video.domain.Comment;
import com.video.domain.Type;
import com.video.domain.User;
import com.video.domain.Video;
import com.video.service.TypeService;
import com.video.service.VideoService;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@RestController
public class IndexController {

    @Resource
    TypeService typeService;

    @Resource
    VideoRepository videoRepository;

    @Resource
    UserRepository userRepository;

    @Resource
    CommentRepository commentRepository;

    @RequestMapping(value = "/exportVideo",method = RequestMethod.POST)
    public String exportVideo() throws IOException {
        List<Video> videoList = videoRepository.findAll();

        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFSheet sheet = wb.createSheet("video数据");

        HSSFRow row = null;

        row = sheet.createRow(0);//创建第一个单元格
        row.setHeight((short) (22.50* 20));
        row.createCell(0).setCellValue("video信息列表");//为第一行单元格设值

        CellRangeAddress rowRegion = new CellRangeAddress(0, 0, 0, 13);
        sheet.addMergedRegion(rowRegion);

        row = sheet.createRow(1);
        row.setHeight((short) (22.50 * 20));//设置行高
        row.createCell(0).setCellValue("视频编号");
        row.createCell(1).setCellValue("视频名称");
        row.createCell(2).setCellValue("视频类别");
        row.createCell(3).setCellValue("视频封面");
        row.createCell(4).setCellValue("视频介绍信息");
        row.createCell(5).setCellValue("视频详情");
        row.createCell(6).setCellValue("视频发布人");
        row.createCell(7).setCellValue("视频状态");
        row.createCell(8).setCellValue("点赞量");
        row.createCell(9).setCellValue("收藏量");
        row.createCell(10).setCellValue("打赏总金额");
        row.createCell(11).setCellValue("评论量");
        row.createCell(12).setCellValue("上传时间");
        row.createCell(13).setCellValue("下载量");


        for (int i = 0; i < videoList.size(); i++) {
            row = sheet.createRow(i + 2);
            Video video = videoList.get(i);
            row.createCell(0).setCellValue(video.getVideoId());
            row.createCell(1).setCellValue(video.getVideoName());
            row.createCell(2).setCellValue(video.getTypeId());
            row.createCell(3).setCellValue(video.getVideoPic());
            row.createCell(4).setCellValue(video.getVideoInfo());
            row.createCell(5).setCellValue(video.getVideoUrl());
            row.createCell(6).setCellValue(video.getVideoUsername());
            row.createCell(7).setCellValue(video.getVideoStatue());
            row.createCell(8).setCellValue(video.getVideoLike());
            row.createCell(9).setCellValue(video.getVideoFavorite());
            row.createCell(10).setCellValue(video.getVideoReward().toString());
            row.createCell(11).setCellValue(video.getVideoComment());
            row.createCell(12).setCellValue(video.getVideoUptime());
            row.createCell(13).setCellValue(video.getVideoDownload());
        }
        sheet.setDefaultRowHeight((short) (16.5 * 20));
        for (int i = 0; i <= 13; i++) {
            sheet.autoSizeColumn(i);
        }
        //response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //OutputStream os = response.getOutputStream();
        //File desktopDir = FileSystemView.getFileSystemView() .getHomeDirectory();
        File file=new File("C:\\Users\\13407\\Desktop\\default.xls");
        OutputStream os=new FileOutputStream(file);
        System.out.println(os);
        //response.setHeader("Content-disposition", "attachment;filename=default.xls");//默认Excel名称
        wb.write(os);
        os.flush();
        os.close();
        return "导出成功";
    }

    @RequestMapping(value = "/exportUser",method = RequestMethod.POST)
    public String exportUser() throws IOException {
        List<User> userList = userRepository.findAll();

        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFSheet sheet = wb.createSheet("user数据");

        HSSFRow row = null;

        row = sheet.createRow(0);//创建第一个单元格
        row.setHeight((short) (22.50* 20));
        row.createCell(0).setCellValue("user信息列表");//为第一行单元格设值

        CellRangeAddress rowRegion = new CellRangeAddress(0, 0, 0, 13);
        sheet.addMergedRegion(rowRegion);

        row = sheet.createRow(1);
        row.setHeight((short) (22.50 * 20));//设置行高
        row.createCell(0).setCellValue("用户编号");
        row.createCell(1).setCellValue("用户名");
        row.createCell(2).setCellValue("用户邮箱");
        row.createCell(3).setCellValue("用户电话");
        row.createCell(4).setCellValue("用户账户余额");
        row.createCell(5).setCellValue("用户头像");
        row.createCell(6).setCellValue("用户真实名称");
        row.createCell(7).setCellValue("用户爱好");
        row.createCell(8).setCellValue("用户状态");
        row.createCell(9).setCellValue("用户充值单号");
        row.createCell(10).setCellValue("用户注册时间");
        row.createCell(11).setCellValue("用户密码");
        row.createCell(12).setCellValue("用户充值VIP单号");
        row.createCell(13).setCellValue("用户验证码");

        for (int i = 0; i < userList.size(); i++) {
            row = sheet.createRow(i + 2);
            User user = userList.get(i);
            row.createCell(0).setCellValue(user.getUserId());
            row.createCell(1).setCellValue(user.getUserName());
            row.createCell(2).setCellValue(user.getUserEmail());
            row.createCell(3).setCellValue(user.getUserTell());
            row.createCell(4).setCellValue(user.getUserMoney().toString());
            row.createCell(5).setCellValue(user.getUserPic());
            row.createCell(6).setCellValue(user.getUserRealname());
            row.createCell(7).setCellValue(user.getUserHobby());
            row.createCell(8).setCellValue(user.getUserStatue());
            row.createCell(9).setCellValue(user.getUserRechargeOrderNumber());
            row.createCell(10).setCellValue(user.getUserUptime());
            row.createCell(11).setCellValue(user.getUserPassword());
            row.createCell(12).setCellValue(user.getUserRechargeVipOrderNumber());
            row.createCell(13).setCellValue(user.getUserStatue());
        }
        sheet.setDefaultRowHeight((short) (16.5 * 20));
        for (int i = 0; i <= 13; i++) {
            sheet.autoSizeColumn(i);
        }
        File file=new File("C:\\Users\\13407\\Desktop\\default.xls");
        OutputStream os=new FileOutputStream(file);
        System.out.println(os);
        wb.write(os);
        os.flush();
        os.close();
        return "导出成功";
    }

    @RequestMapping(value = "/exportComment",method = RequestMethod.POST)
    public String exportComment() throws IOException {
        List<Comment> commentList = commentRepository.findAll();

        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFSheet sheet = wb.createSheet("comment数据");

        HSSFRow row = null;

        row = sheet.createRow(0);//创建第一个单元格
        row.setHeight((short) (22.50* 20));
        row.createCell(0).setCellValue("comment信息列表");//为第一行单元格设值

        CellRangeAddress rowRegion = new CellRangeAddress(0, 0, 0, 10);
        sheet.addMergedRegion(rowRegion);

        row = sheet.createRow(1);
        row.setHeight((short) (22.50 * 20));//设置行高
        row.createCell(0).setCellValue("评论编号");
        row.createCell(1).setCellValue("视频编号");
        row.createCell(2).setCellValue("评论用户编号");
        row.createCell(3).setCellValue("评论用户名");
        row.createCell(4).setCellValue("评论用户头像");
        row.createCell(5).setCellValue("被评论用户编号");
        row.createCell(6).setCellValue("被评论用户名");
        row.createCell(7).setCellValue("评论内容");
        row.createCell(8).setCellValue("评论状态");
        row.createCell(9).setCellValue("评论时间");
        row.createCell(10).setCellValue("评论点赞量");

        for (int i = 0; i < commentList.size(); i++) {
            row = sheet.createRow(i + 2);
            Comment comment = commentList.get(i);
            row.createCell(0).setCellValue(comment.getCommentId());
            row.createCell(1).setCellValue(comment.getVideoId());
            row.createCell(2).setCellValue(comment.getUserId());
            row.createCell(3).setCellValue(comment.getUserName());
            row.createCell(4).setCellValue(comment.getUserPic());
            row.createCell(5).setCellValue(comment.getRespondentId());
            row.createCell(6).setCellValue(comment.getRespondentName());
            row.createCell(7).setCellValue(comment.getCommentContent());
            row.createCell(8).setCellValue(comment.getCommentStatue());
            row.createCell(9).setCellValue(comment.getCommentTime());
            row.createCell(10).setCellValue(comment.getCommentCount());
        }
        sheet.setDefaultRowHeight((short) (16.5 * 20));
        for (int i = 0; i <= 13; i++) {
            sheet.autoSizeColumn(i);
        }
        File file=new File("C:\\Users\\13407\\Desktop\\default.xls");
        OutputStream os=new FileOutputStream(file);
        System.out.println(os);
        wb.write(os);
        os.flush();
        os.close();
        return "导出成功";
    }



    @RequestMapping(value = "/importData")
    public String exImport(@RequestParam(value = "filename")MultipartFile file, HttpSession session) {
        boolean a = false;
        String fileName = file.getOriginalFilename();
        try {
            a = typeService.batchImport(fileName, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:index";
    }
}