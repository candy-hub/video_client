package com.video.utils;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Description:
 * @Company: NB
 * @Author: Li Jia
 * @Date: 2019/11/14
 * @Time: 17:10
 */
@Component
public class EsUtils {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    public String CreateIndex() throws IOException {
        //创建索引请求对象，并设置索引名称
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("video-test");
        //设置索引参数
        createIndexRequest.settings(Settings.builder().put("number_of_shards",1).put("number_of_replicas",0));
        //设置映射
        createIndexRequest.mapping("doc","{\n" +
                "                \"properties\": {\n" +
                "                    \"idx_video_info\": {\n" +
                "                        \"type\": \"text\",\n" +
                "                        \"analyzer\": \"ik_max_word\",\n" +
                "                        \"search_analyzer\": \"ik_smart\"\n" +
                "                    },\n" +
                "                    \"idx_video_name\": {\n" +
                "                        \"type\": \"text\",\n" +
                "                        \"analyzer\": \"ik_max_word\",\n" +
                "                        \"search_analyzer\": \"ik_smart\"\n" +
                "                    },\n" +
                "                     \"idx_video_username\": {\n" +
                "                        \"type\": \"text\",\n" +
                "                        \"analyzer\": \"ik_max_word\",\n" +
                "                        \"search_analyzer\": \"ik_smart\"\n" +
                "                    },\n" +
                "                    \"pk_video_id\":{\n" +
                "                        \"type\":\"text\",\n" +
                "                        \"index\":false\n" +
                "                    },\n" +
                "                    \"idx_video_pic\":{\n" +
                "                        \"type\":\"text\",\n" +
                "                        \"index\":false\n" +
                "                    },\n" +
                "                     \"idx_video_url\":{\n" +
                "                        \"type\":\"text\",\n" +
                "                        \"index\":false\n" +
                "                    }\n" +
                "                }\n" +
                "            }", XContentType.JSON);
        //创建索引操作客户端
        IndicesClient indices=restHighLevelClient.indices();
        //创建响应对象
        CreateIndexResponse createIndexResponse = indices.create(createIndexRequest);
        //得到响应结果
        boolean acknowledged = createIndexResponse.isAcknowledged();
        if(acknowledged){
            System.out.println("索引库创建成功！");
            return "1";
        }
        return "0";
    }
}
