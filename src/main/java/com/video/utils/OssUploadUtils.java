package com.video.utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import com.video.config.ConstantConfig;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.util.Date;


@Component
public class OssUploadUtils {

    @Autowired
    private ConstantConfig constantConfig;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(OssUploadUtils.class);

    //文件上传
    public String upLoad(File file) throws Throwable {
        logger.info("------OSS文件上传开始--------"+ logger.getName());
        String endpoint=constantConfig.getLXIMAGE_END_POINT();
        //System.out.println("获取到的Point为:"+endpoint);
        String accessKeyId=constantConfig.getLXIMAGE_ACCESS_KEY_ID();
        String accessKeySecret=constantConfig.getLXIMAGE_ACCESS_KEY_SECRET();
        String bucketName=constantConfig.getLXIMAGE_BUCKET_NAME();
        String filePath=constantConfig.getLXIMAGE_FILE_PATH();
       /* SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        String dateStr=format.format(new Date());*/

        // 判断文件是否为空
        if(file==null){
            return "0";//0代表文件为空
        }
        //创建oss实例
        OSSClient client=new OSSClient(endpoint, accessKeyId, accessKeySecret);
        ObjectMetadata meta = new ObjectMetadata();
        // 指定上传的内容类型。
        meta.setContentType("text/plain");
        try{
            // 判断容器是否存在,不存在就创建
            if (!client.doesBucketExist(bucketName)) {
                client.createBucket(bucketName);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                client.createBucket(createBucketRequest);
            }
            //设置文件路径和名称
           // String fileUrl =filePath+"-"+file.getName();
            //断点续传
            //通过UploadFileRequest设置多个参数
            UploadFileRequest uploadFileRequest = new UploadFileRequest(bucketName,file.getName());
            // 指定上传的本地文件。
            uploadFileRequest.setUploadFile(file.getPath());
            // 指定上传并发线程数，默认为1。
            uploadFileRequest.setTaskNum(5);
            // 指定上传的分片大小，范围为100KB~5GB，默认为文件大小/10000。
            uploadFileRequest.setPartSize(1 * 1024 * 1024);
            // 开启断点续传，默认关闭。
            uploadFileRequest.setEnableCheckpoint(true);
            // 记录本地分片上传结果的文件。开启断点续传功能时需要设置此参数，上传过程中的进度信息会保存在该文件中，如果某一分片上传失败，再次上传时会根据文件中记录的点继续上传。上传完成后，该文件会被删除。默认与待上传的本地文件同目录，为uploadFile.ucp。
            uploadFileRequest.setCheckpointFile("CheckpointFile");
            // 文件的元数据。
            uploadFileRequest.setObjectMetadata(meta);
            // 断点续传上传。
            UploadFileResult result = client.uploadFile(uploadFileRequest);
            // 设置权限(公开读)
            client.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            if (result != null) {
                logger.info("------OSS文件上传成功------" + file.getName());
                //获得oss视频的外链
                // 设置URL过期时间为10年  3600l* 1000*24*365*10
                Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
                // 生成URL
                URL url = client.generatePresignedUrl(bucketName, file.getName(), expiration);
                return url.toString();
             }else {
                return "0";
            }
        }catch (Exception e){
            logger.info(e.getMessage());
        }finally {
            client.shutdown();
        }
        return null;
        }

}
