package org.wayne;

import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Description:
 * @author: lwq
 */
public class Demo1 {
    public static void main(String[] args) throws IOException {

        final String host = "192.168.2.2";
        final RestClientBuilder builder = RestClient.builder(
                new HttpHost(host,9200,"http"),
                new HttpHost(host,9201,"http"),
                new HttpHost(host,9202,"http")
        );
//        设置请求头
//        builder.setDefaultHeaders()
        RestClient client = builder.build();
        // 构建请求
        Request request = new Request("GET","/books/_search");
        // 添加请求体 // 操作技巧,选中空字符串 alt+enter 选择Inject language or reference在选对于的语言文件,会根据方法目标自动映射,由特定的Json编辑面板自动转义
        request.setEntity(new NStringEntity("", ContentType.APPLICATION_JSON));
        // 同步
        final Response response = client.performRequest(request);
        final BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String s= br.readLine();
        System.out.println(s);
        br.close();

        // 异步
        client.performRequestAsync(request, new ResponseListener() {
            // 成功回调
            @Override
            public void onSuccess(Response response) {

            }
            // 失败回调
            @Override
            public void onFailure(Exception e) {

            }
        });
        // 关闭客户端
        client.close();

    }
}
