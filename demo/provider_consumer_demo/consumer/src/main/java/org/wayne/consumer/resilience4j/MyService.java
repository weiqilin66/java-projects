package org.wayne.consumer.resilience4j;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @Description: 重试/熔断/限流
 *  限流是用来保护服务端接口的 在provide中实现
 * @author: LinWeiQi
 */
@Service
//@Retry(name = "retryA")//开启重试 retryA配置文件中定义配置
@CircuitBreaker(name = "cbA",fallbackMethod = "error")//开启熔断
public class MyService {

    @Resource
    RestTemplate restTemplate;

    public void retry1(){
        String s = restTemplate.getForObject("http://provide/error1", String.class);
        System.out.println(s);
    }
    //此处的异常必须捕获Throwable
    public void error(Throwable throwable){
        System.out.println("error");
    }

    public void reateLimit(){
        String s = restTemplate.getForObject("http://provide/hello", String.class);
        System.out.println(s);
    }
}
