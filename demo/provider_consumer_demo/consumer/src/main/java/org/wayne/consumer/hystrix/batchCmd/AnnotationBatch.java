package org.wayne.consumer.hystrix.batchCmd;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.wayne.demo.User;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Description: 合并请求-注解方式
 * 触发: 指定时间内高频的访问同一个接口且只有参数不同
 * @author: LinWeiQi
 */
@Service
public class AnnotationBatch {

    @Resource
    RestTemplate restTemplate;

    //合并请求核心注解
    @HystrixCollapser(batchMethod = "getUserByIds",collapserProperties = {
            @HystrixProperty(name = "timerDelayInMilliseconds",value = "200")
    })
//    @HystrixCommand(fallbackMethod = "error") 和合并请求注解不能同时使用
    public Future<User> getUserById(Integer id){
        return null;
    }

    @HystrixCommand
    public List<User> getUserByIds(List<Integer> ids){
        User[] users = restTemplate.getForObject("http://provide/user/{1}", User[].class, StringUtils.join(ids, ","));
        return Arrays.asList(users);
    }


}
