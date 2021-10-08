package org.wayne.consumer.resttemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.wayne.demo.User;

import javax.annotation.Resource;
import java.net.URI;

/**
 * @Description: post和get方式类似
 * 多出一个 postForLocation
 * 执行完post请求后进行重定向,例如注册成功后重定向到登录页
 * 重定向接口 成功响应码302
 *
 * 分辨key-value传参和Json传参区别
 * @author: LinWeiQi
 */
@RestController
@RequestMapping("/restTemplate")
public class PostController {

    @Autowired// Qualifier不写默认找的变量名restTemplate
    @Qualifier("restTemplate")
    RestTemplate restTemplate;

    @Autowired
    @Qualifier("restTemplateOne")//没有负载均衡的restTemplate
    RestTemplate restTemplateOne;

    @RequestMapping("user1")
    public User getUser() {//key value传参
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("username", "wayne");
        return restTemplate.postForObject("http://provide/user1", map, User.class);
    }

    @RequestMapping("user2")
    public User getUser2() {//json传参
        User user = new User();
        user.setUsername("wayne");
        return restTemplate.postForObject("http://provide/user2", user, User.class);
    }

    @GetMapping("/register")
    public String register() {
        //访问的必须是重定向接口
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("username", "wayne");
        URI uri = restTemplate.postForLocation("http://provide/register", map);
        System.out.println(uri);
        return restTemplate.getForObject(uri,String.class);
    }
}
