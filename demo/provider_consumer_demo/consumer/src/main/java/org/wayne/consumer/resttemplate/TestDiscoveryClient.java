package org.wayne.consumer.resttemplate;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * DiscoveryClient + HttpURLConnection 原始http调用远程接口
 * discoveryClient.getInstances("xx") 获取缓存的注册表信息
 * @return
 */
@RestController
public class TestDiscoveryClient {

    @Resource
    DiscoveryClient discoveryClient;

    @RequestMapping("/hello")
    public String hello1() {
        //从注册中心获取到的注册表信息
        List<ServiceInstance> list = discoveryClient.getInstances("provide");
        ServiceInstance serviceInstance = list.get(0);
        String hostName = serviceInstance.getHost();
        int port = serviceInstance.getPort();
        StringBuilder sb = new StringBuilder();
        sb.append("http://")
                .append(hostName)
                .append(":")
                .append(port)
        .append("/hello");
        System.out.println(sb);
        HttpURLConnection con = null;
        String address = sb.toString();
        try {
            //http方式访问接口
            URL url = new URL(address);
            con = (HttpURLConnection) url.openConnection();
            if (con.getResponseCode()==200) {
                BufferedReader read = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String s = read.readLine();
                read.close();
                System.out.println(s);
                return s;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "error";
    }
}
