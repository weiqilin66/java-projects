package org.wayne.roboot.service;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@Slf4j
@Service
public class SendFeishu implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 飞书接口地址
     */
    private String url;
    /**
     * 发送内容
     */
    private String content;
    private String contentType = "application/json; charset=utf-8";

    /**
     * 标题
     */
    private String subject;

    /**
     * 发送飞书
     */
    public Map<String, String> send() throws IOException {
        Map<String, String> map = new HashMap<>();
        HttpClient httpClient = new HttpClient();//客户端实例化
        PostMethod postMethod = new PostMethod(url);//请求路径
        postMethod.addRequestHeader("Content-type", contentType);
        try {
            byte[] requestBytes = content.getBytes("utf-8");//参数转化为二进制流
            InputStream inputStream = new ByteArrayInputStream(requestBytes, 0, requestBytes.length);

            InputStreamRequestEntity requestEntity = new InputStreamRequestEntity(inputStream, requestBytes.length, contentType);//请求体
            postMethod.setRequestEntity(requestEntity);//参数放入请求体
            httpClient.executeMethod(postMethod);//执行方法
            byte[] responseBody = postMethod.getResponseBody();//返回参数
            String s = new String(responseBody);
            System.out.println(s);
            map.put("message", s);
        } catch (Exception e) {
            log.error("飞书发送异常");
        } finally {
            log.info("关闭链接");
            postMethod.releaseConnection();
            httpClient.getHttpConnectionManager().closeIdleConnections(0);
        }
        return map;
    }
}
