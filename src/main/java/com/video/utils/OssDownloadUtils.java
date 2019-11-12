package com.video.utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.DownloadFileRequest;
import com.aliyun.oss.model.DownloadFileResult;
import com.aliyun.oss.model.ObjectMetadata;
import com.video.config.ConstantConfig;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class OssDownloadUtils {

    @Autowired
    private ConstantConfig constantConfig;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(OssUploadUtils.class);

    //文件下载
    public String downLoad(String name) throws Throwable {
        logger.info("------OSS文件下载开始--------" + logger.getName());
        String endpoint = constantConfig.getLXIMAGE_END_POINT();
        //System.out.println("获取到的Point为:"+endpoint);
        String accessKeyId = constantConfig.getLXIMAGE_ACCESS_KEY_ID();
        String accessKeySecret = constantConfig.getLXIMAGE_ACCESS_KEY_SECRET();
        String bucketName = constantConfig.getLXIMAGE_BUCKET_NAME();
        //String filePath=constantConfig.getLXIMAGE_FILE_PATH();

        //创建oss实例
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 下载请求，10个任务并发下载，启动断点续传。
        DownloadFileRequest downloadFileRequest = new DownloadFileRequest(bucketName, name);
        downloadFileRequest.setDownloadFile("C:\\Users\\12209\\Desktop");
        downloadFileRequest.setPartSize(1 * 1024 * 1024);
        downloadFileRequest.setTaskNum(10);
        downloadFileRequest.setEnableCheckpoint(true);
        downloadFileRequest.setCheckpointFile("CheckpointFile");
        // 下载文件。
        DownloadFileResult downloadRes = client.downloadFile(downloadFileRequest);
        // 下载成功时，会返回文件元信息。
        ObjectMetadata metadata = downloadRes.getObjectMetadata();
        // 关闭OSSClient。
        client.shutdown();
        if (metadata == null) {
            return "0";
        }else {
            return "1";
        }
    }
}
