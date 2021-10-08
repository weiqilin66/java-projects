package org.wayne.consumer.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;

/**
 * @Description:
 * 1. 服务端熔断服务降级,consumer代码出错也会导致服务降级
 * 2. 请求缓存功能:当请求参数不变时,返回之前缓存的数据(主流redis)
 * @author: LinWeiQi
 */
@Service
public class HystrixAnnotation {
    @Autowired
    RestTemplate restTemplate;
    /**
     * 服务降级
     *
     * 此方法发起了远程调用,假设方法失败了
     * @HystrixCommand(fallbackMethod = "error")注解表示hello方法失败时调用的方法error
     * 方法error的返回值和hello方法返回值必须一致,参数也一致(忽略Throwable参数)
     *
     * 请求缓存
     * 例子1: 数据库查询失败了 fallbackMethod定义一个方法去缓存查这个数据
     * 例子2: 启动了2个provide实例,正常情况下负载均衡consumer可以访问到2个服务
     * 关闭其中一个服务,这个时候consumer中从注册中心获取到的信息不会立即更新其中一个服务掉线,就可能访问这个接口
     * 此时服务就掉不通了,进入了error方法,不回给用户返回一个错误页面
     *
     * error方法也可以继续降级
     * 使用 @HystrixCommand(fallbackMethod = "error2")
     *
     */
    @HystrixCommand(fallbackMethod = "error")
    public String hello(){

        return restTemplate.getForObject("http://provide/hello",String.class);
    }

    //异步调用的注解写法
    @HystrixCommand(fallbackMethod = "error")
    public Future<String> hello2(){
        return new AsyncResult<String>() {
            @Override
            public String invoke() {
                return restTemplate.getForObject("http://provide/hello",String.class);
            }
        };
    }
    //Throwable参数用于捕获consumer端报错导致的服务降级的错误
    public String error(Throwable throwable){
        System.out.println(throwable.getMessage());
        return "error";
    }
    //ignoreExceptions指定发生某个异常时不要服务降级,直接抛出异常
    @HystrixCommand(fallbackMethod = "error",ignoreExceptions = ArithmeticException.class)
    @CacheResult //搭配HystrixRequestContext缓存生命周期,缓存的key是方法的参数(多个参数可@cahekey指定某个参数为准,不加为所有参数为key) value是方法的返回值
    public String hello4(String name){
        int i= 1/0;
        return restTemplate.getForObject("http://provide/hello",String.class);
    }
    //缓存生命周期
    //合并请求需要开启缓存
    public void hc(){
        //开始生效
        HystrixRequestContext hystrixRequestContext = HystrixRequestContext.initializeContext();
        //缓存失效
        hystrixRequestContext.close();

    }
    //删除接口删除缓存中的数据 @CacheRemove(commandKey) commandKey为添加缓存方法的方法名

}
