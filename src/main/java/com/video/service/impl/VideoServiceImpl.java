package com.video.service.impl;

import com.video.dao.CollectionRepository;
import com.video.dao.VideoRepository;
import com.video.domain.Collection;
import com.video.domain.Video;
import com.video.response.Pagination;
import com.video.service.VideoService;
import com.video.utils.EsUtils;
import com.video.utils.OssDownloadUtils;
import com.video.utils.OssUploadUtils;
import com.video.utils.QiniuUploadUtils;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;


/**
 * @Description:
 * @Company: NB
 * @Author: Li Jia
 * @Date: 2019/11/11
 * @Time: 14:54
 */
@Service
public class VideoServiceImpl implements VideoService{

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private OssUploadUtils ossUploadUtils;

    @Autowired
    private QiniuUploadUtils qiniuUploadUtils;

    @Autowired
    private OssDownloadUtils ossDownloadUtils;

    @Autowired
    private EsUtils esUtils;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private CollectionRepository collectionRepository;

    private static String objectName;


    @Override
    public String upload(MultipartFile file) {
        objectName= file.getOriginalFilename();

        //System.out.println(filename);
        try {
            if (file!=null) {
                if (!"".equals(objectName.trim())) {
                    File newFile = new File(objectName);
                    FileOutputStream os = new FileOutputStream(newFile);
                    os.write(file.getBytes());
                    os.close();
                    file.transferTo(newFile);

                    // 上传到OSS
                    String s = ossUploadUtils.upLoad(newFile);
                    //判断是否上传成功
                    if(s.equals(0)){
                        //失败返回0
                        return "0";
                    }else {
                        System.out.println("外链为===="+s);
                        return s;
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public String addVideo(Video video) throws IOException {
        //System.out.println(objectName);
        video.setVideoLike(0);
        video.setVideoFavorite(0);
        video.setVideoComment(0);
        BigDecimal bigDecimal=new BigDecimal(0.00);
        video.setVideoReward(bigDecimal);
        video.setVideoDownload(0);
        video.setVideoStatue(0);
        video.setVideoObjectName(objectName);
        video.setVideoUptime(new Date());
        Video save = videoRepository.save(video);
        Map map=new HashMap();
        map.put("pk_video_id",save.getVideoId());
        map.put("idx_video_info",save.getVideoInfo());
        map.put("idx_video_name",save.getVideoName());
        map.put("idx_video_username",save.getVideoUsername());
        map.put("idx_video_pic",video.getVideoPic());
        map.put("idx_video_url",video.getVideoUrl());
        IndexRequest indexRequest=new IndexRequest("video-test","doc");
        indexRequest.id(save.getVideoId()+"");
        IndexRequest source = indexRequest.source(map);
        restHighLevelClient.index(source);
        return "1";
    }

    @Override
    public String uploadPic(MultipartFile file) {
        if (Objects.isNull(file) || file.isEmpty()) {
            return "fail";
        }else {
            String path = qiniuUploadUtils.upload(file);
            return path;
        }
     }

    @Override
    public String deleteVideo(Integer id) throws IOException {
        videoRepository.deleteById(id);
        DeleteRequest deleteRequest = new DeleteRequest("items-test", "doc", id + "");
        DeleteResponse delete = restHighLevelClient.delete(deleteRequest);
        return "1";
    }

    @Override
    public String download(Video video) {
        String name = video.getVideoObjectName();
        try {
            String s = ossDownloadUtils.downLoad(name);
            if(s.equals(0)){
                //失败返回0
                return "0";
            }else{
                //获取当前视频的下载量
                Integer videoDownload = video.getVideoDownload();
                int i = videoDownload.intValue() + 1;
                Integer down = Integer.valueOf(i);
                video.setVideoDownload(down);
                videoRepository.save(video);
                return "1";
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public String im() throws IOException {
        String s = esUtils.CreateIndex();
        if (s.equals("1")) {
            List<Video> videos = videoRepository.findAll();
            Map map=new HashMap();
            for(Video video:videos){
                map.put("pk_video_id",video.getVideoId());
                map.put("idx_video_info",video.getVideoInfo());
                map.put("idx_video_name",video.getVideoName());
                map.put("idx_video_username",video.getVideoUsername());
                map.put("idx_video_pic",video.getVideoPic());
                map.put("idx_video_url",video.getVideoUrl());
                IndexRequest indexRequest = new IndexRequest("video-test", "doc");
                indexRequest.id(video.getVideoId()+"");
                IndexRequest source = indexRequest.source(map);
                IndexResponse index = restHighLevelClient.index(source);
                //DocWriteResponse.Result result = index.getResult();
            }
            return "导入成功！";
        }
        return "导入失败！";
    }

    @Override
    public List<Map> search(String searchName) throws IOException {
        //搜索请求对象
        SearchRequest searchRequest = new SearchRequest("video-test");
        //设置类型
        searchRequest.types("doc");
        //搜索源构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        if(searchName.equals("null")){
            searchSourceBuilder.query(QueryBuilders.matchAllQuery());
            searchRequest.source(searchSourceBuilder);
        }else {
            MultiMatchQueryBuilder matchQueryBuilder = QueryBuilders.multiMatchQuery(searchName);
            searchSourceBuilder.query(matchQueryBuilder);
            //设置搜索源
            searchRequest.source(searchSourceBuilder);
        }
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest);
        SearchHits hits = searchResponse.getHits();
        //long totalHits = hits.getTotalHits();
        List<Map> list = new ArrayList();
        for (SearchHit hit : hits) {
            String id = hit.getId();
            //源文档内容
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            list.add(sourceAsMap);
            //System.out.println(sourceAsMap);
        }
        return list;
    }

    @Override
    public String favorite(Integer userId,Integer videoId) {
        List<Collection> list = collectionRepository.findAllByUserIdAndVideoId(userId, videoId);
        if(list.size()>0){
            return "0";//已存在
        }else{
            Video video = videoRepository.findById(videoId).get();
            Collection collection=new Collection();
            collection.setUserId(userId);
            collection.setVideoId(videoId);
            collectionRepository.save(collection);
            //获取当前视频的收藏量
            Integer videoFavorite = video.getVideoFavorite();
            int i = videoFavorite.intValue() + 1;
            Integer favorite = Integer.valueOf(i);
            video.setVideoFavorite(favorite);
            videoRepository.save(video);
            return "1";//加入
        }

    }

    @Override
    public String like(Integer id) {
        Video video = videoRepository.findById(id).get();
        Integer videoLike = video.getVideoLike();
        int i = videoLike.intValue() + 1;
       // System.out.println(i);
        Integer like = Integer.valueOf(i);
       // System.out.println("======"+like);
        video.setVideoLike(like);
        videoRepository.save(video);
        return "1";
    }

    @Override
    public Video findVideoByVideoId(Integer id) {
        Video video = videoRepository.findById(id).get();
        return video;
    }

    @Override
    public Pagination findVideoByUserId(Integer id, Integer page, Integer size) {
        Pageable pages = PageRequest.of(page - 1, size);
        Page<Video> all = videoRepository.findAllByUserId(id, pages);
        Pagination res = new Pagination();
        res.setList(all.getContent());
        res.setTotal(all.getTotalElements());
        return res;
    }
}


