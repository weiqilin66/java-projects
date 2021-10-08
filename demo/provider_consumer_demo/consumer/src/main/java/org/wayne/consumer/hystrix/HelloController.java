package org.wayne.consumer.hystrix;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.wayne.consumer.hystrix.batchCmd.AnnotationBatch;
import org.wayne.consumer.hystrix.batchCmd.UserCollapseCommand;
import org.wayne.consumer.hystrix.batchCmd.UserService;
import org.wayne.demo.User;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Description:  熔断-服务降级(eg:数据库查->缓存查->final值)
 * @author: LinWeiQi
 */
@RestController("hystrixHelloController")
@RequestMapping("/hystrix")
public class HelloController {

    @Autowired
    HystrixAnnotation helloService;

    @Resource
    @Qualifier("restTemplate")
    RestTemplate restTemplate;

    @GetMapping("/hello")
    public String hello(){
        //注解方式
        return helloService.hello();
    }

    @GetMapping("/hello2")
    public String hello2(){
        //请求命令方式,可异步
        MyHystrixCommand testHystrixCommandTest = new MyHystrixCommand(
                com.netflix.hystrix.HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("wayne")),restTemplate);
//        String s = testHystrixCommand.execute();//执行run方式1
        Future<String> queue = testHystrixCommandTest.queue();//方式2,可异步调用,先入队,再调用
        String s = null;
        try {
            s = queue.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return s;
    }

    @GetMapping("/hello3")
    public String hello3() throws ExecutionException, InterruptedException {
        //注解方式,异步
        Future<String> stringFuture = helloService.hello2();//入队
        String s = stringFuture.get();//可异步取
        return s;
    }
    @Autowired
    UserService userService;

    //测试合并请求
    @GetMapping("/hello5")
    public void hello5() throws ExecutionException, InterruptedException {
        HystrixRequestContext ctx = HystrixRequestContext.initializeContext();//缓存
        UserCollapseCommand cmd1 = new UserCollapseCommand(userService, 99);
        UserCollapseCommand cmd2 = new UserCollapseCommand(userService, 98);
        UserCollapseCommand cmd3 = new UserCollapseCommand(userService, 97);
        Future<User> q1 = cmd1.queue();
        Future<User> q2 = cmd2.queue();
        Future<User> q3 = cmd3.queue();
        User u1 = q1.get();
        User u2 = q2.get();
        User u3 = q3.get();
        System.out.println(u1);
        System.out.println(u2);
        System.out.println(u3);
        Thread.sleep(2000);
        UserCollapseCommand cmd4 = new UserCollapseCommand(userService, 96);
        Future<User> q4 = cmd4.queue();
        User u4 = q4.get();
        System.out.println(u4);
        ctx.close();
    }

    @Autowired
    AnnotationBatch annotationBatch;
    @GetMapping("/hello6")
    public void hello6() throws ExecutionException, InterruptedException {
        //合并请求必须开启缓存
        HystrixRequestContext ctx = HystrixRequestContext.initializeContext();
        Future<User> q1 = annotationBatch.getUserById(99);
        Future<User> q2 = annotationBatch.getUserById(98);
        Future<User> q3 = annotationBatch.getUserById(97);
        User u1 = q1.get();
        User u2 = q2.get();
        User u3 = q3.get();
        System.out.println(u1);
        System.out.println(u2);
        System.out.println(u3);
        Thread.sleep(2000);
        Future<User> q4 = annotationBatch.getUserById(96);
        User u4 = q4.get();
        System.out.println(u4);
        ctx.close();
    }
}
