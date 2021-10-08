package org.wayne.thief.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @Description:
 * @author: lwq
 */
public class ChromeDriverProxy extends ChromeDriver {
//    public static final Logger log = LoggerFactory.getLogger(ChromeDriverProxy.class);
//    private static final int COMMAND_TIMEOUT = 5000;
//    // 必须固定端口，因为ChromeDriver没有实时获取端口的接口；
//    private static final int CHROME_DRIVER_PORT = 9999;
//
//    private static ChromeDriverService driverService = new ChromeDriverService.Builder().usingPort(CHROME_DRIVER_PORT).build();
//
//    public ChromeDriverProxy(ChromeOptions options) {
//        super(driverService, options);
//    }
//
//    // 根据请求ID获取返回内容
//
//    public ResponseBodyVo getResponseBody(String requestId) {
//        ResponseBodyVo result = null;
//        try {
//            // CHROME_DRIVER_PORT chromeDriver提供的端口
//            String url = String.format("http://localhost:%s/session/%s/goog/cdp/execute", CHROME_DRIVER_PORT, getSessionId());
//            HttpPost httpPost = new HttpPost(url);
//            JSONObject object = new JSONObject();
//            JSONObject params = new JSONObject();
//            params.put("requestId", requestId);
//            object.put("cmd", "Network.getResponseBody");
//            object.put("params", params);
//            httpPost.setEntity(new StringEntity(object.toString()));
//            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(COMMAND_TIMEOUT).setConnectTimeout(COMMAND_TIMEOUT).build();
//            CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
//            HttpResponse response = httpClient.execute(httpPost);
//            JSONObject data = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
//            return JSONObject.toJavaObject(data, ResponseBodyVo.class);
//        } catch (IOException e) {
//            log.error("getResponseBody failed!", e);
//        } return result;
//
//    }
}