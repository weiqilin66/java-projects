package org.wayne.es.demo;

import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @author: lwq
 */
public class Demo1 {
    public static void main(String[] args) throws IOException, InterruptedException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")
                        ));
        // 添加文档
        IndexRequest request = new IndexRequest("posts", "doc", "1");
        String jsonString = "{\"user\":\"kimchy\",\"postDate\":\"2013-01-30\",\"message\":\"trying out Elasticsearch\"}";
        // 可以使用map<String,Object>存储
        request.source(new HashMap<>());
        request.source(jsonString, XContentType.JSON);
        final IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        // 查询
        final GetRequest getRequest = new GetRequest("posts", "doc", "1");
        final GetResponse documentFields = client.get(getRequest, RequestOptions.DEFAULT);
        final Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();
        for (Map.Entry<String, Object> entry : sourceAsMap.entrySet()) {
            System.out.println(entry.getValue());
            System.out.println(entry.getKey());
        }
        Thread.sleep(5000);
        client.close();

    }

}
