package org.wayne.provide.controller;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.wayne.demo.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description:  @RateLimiter(name = "rA")//开启限流
 * @author: LinWeiQi
 */
@RestController
public class ProvideController {

    @Value("${server.port}")
    String port;

    @RequestMapping("/hello")
    @RateLimiter(name = "rA")//开启限流
    public String hello(){
        System.out.println(new Date());
        return "hello, i come from "+port;
    }
    @RequestMapping("/error1")
    public String error1(){

        String s = "hello, i come from " + port;
        System.out.println(s);
        int i =1/0;
        return s;
    }
    @RequestMapping("/hello2")
    public String hello2(@RequestParam("name") String name){
        //@RequestParam注解后参数为必传否则400 BadRequest错误
        // 类似的加了@RequestBody没传Json也是
        return name;
    }
    @RequestMapping("/hello3")
    public String hello2(String name,int age){
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(",").append(age);
        return sb.toString();
    }

    @PostMapping("/user1")//key value方式
    public User post1(User user){
        return user;
    }
    @PostMapping("/user2")//JSON方式传参
    public User post(@RequestBody User user){
        return user;
    }

    @DeleteMapping("/user3/{id}")
    public String user3(@PathVariable Integer id){
        return "del success:"+id;
    }
    //普通请求方式 一个请求发送一个
    @GetMapping("/comUser")
    public User getUserById(Integer id){
        return  new User();
    }

    //请求合并,ids多种处理方式都可以,这里举例pathVariable (consumer传入1,2,3,4) 这个接口也可以处理单个请求
    @GetMapping("/user/{ids}")
    public List<User> getUserByIds(@PathVariable String ids){
        String[] split = ids.split(",");
        //模拟业务 实际可能是查数据库
        List<User> users = new ArrayList<>();
        for (String s : split) {
            User user = new User();
            user.setId(Integer.parseInt(s));
            users.add(user);
        }
        return users;
    }
    @GetMapping("/testTO")
    public String testTimeOut(int ms) throws InterruptedException {
        System.out.println("接收到请求");
        Thread.sleep(ms);
        System.out.println("请求被处理");
        return "s";
    }
}
