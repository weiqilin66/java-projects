package org.wayne.consumer.resttemplate;

import jdk.management.resource.internal.inst.SocketOutputStreamRMHooks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

/**
 * @Description: RestTemplate+ribbon 消费服务
 * 启动类注入一个restTemplate开启负载均衡
 * 分辨开启负载均衡后的的区别
 * <p>
 * RestTemplate 是Spring 3.0的一个 http请求工具 --->类比前端的axios
 * 提供了常见的GET POST DELETE PUT等请求方式及通用的exchange,execute方法
 * @author: LinWeiQi
 */
@RestController
@RequestMapping("/restTemplate")
public class GetController {

    @Resource
    RestTemplate restTemplate;

    @Autowired
    @Qualifier("restTemplateOne")
    RestTemplate restTemplateOne;

    /**
     * getForObject() 返回一个对象,既服务端返回的具体值
     * getForEntity() 返回一个ResponseEntity,不仅包含具体值,还包含http请求头信息
     */
    @RequestMapping("/hello")
    public String hello() throws UnsupportedEncodingException {
        //开启负载均衡会自动被拦截,(开启负载均衡本质是添加了拦截器) discoveryClient.getInstances(provide) 从注册表中获得provide地址列表
        //provide会被翻译成实际的服务实例标识

        //3种重载方法
        String s = restTemplate.getForObject("http://provide/hello", String.class);//不传参

        String url = "http://provide/hello2?name=" + URLEncoder.encode("李四", "UTF-8");
        URI uri = URI.create(url);
        String s2 = restTemplate.getForObject(uri, String.class);//URI 传中文

        HashMap<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("name", "wayne");
        uriVariables.put("age", 18);
        String s3 = restTemplate.getForObject("http://provide/hello2?name={1}", String.class, "wayne");//占位传参

        String s4 = restTemplate.getForObject("http://provide/hello3?name={name}&age={age}", String.class, uriVariables);//map传参
        return s;
    }


    /**
     * hello1()不可用
     *
     * 开启负载均衡后 restTemplate.getForObject(url，args) url只能传服务名
     * 底层通过discoveryClient.getInstances(截取url得到服务名) 从注册表中获得 host/port信息 在发送请求
     * 非负载均衡截取到的是主机名LAPTOP-3MG3GTA9报错找不到这个服务实例
     *
     * Exception：java.lang.IllegalStateException: No instances available for LAPTOP-3MG3GTA9
     */
    @Resource
    DiscoveryClient discoveryClient;

    @RequestMapping("/hello1")
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
        String s = "";
        try {
            s = restTemplate.getForObject(sb.toString(), String.class);
            return s;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("开启负载均衡的restTemplate只能指定服务名否者discoveryClient.getInstances(address)报错");
        }
        s= restTemplateOne.getForObject(sb.toString(), String.class);
        return s;
    }
}
