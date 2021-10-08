package org.wayne.consumer.hystrix;

import com.netflix.hystrix.HystrixCommand;
import org.springframework.web.client.RestTemplate;

/**
 * @Description: 继承的方式代替注解写法
 * @author: LinWeiQi
 */
// run 方法返回值类型与定义的继承泛型HystrixCommand<String>一致
public class MyHystrixCommand extends HystrixCommand<String> {

    //非注入
    RestTemplate restTemplate;

    public MyHystrixCommand(Setter setter, RestTemplate restTemplate) {
        super(setter);
        this.restTemplate = restTemplate;
    }
    // 熔断功能的方法主题,注意类似线程不能直接.run()去调用
    @Override
    protected String run() throws Exception {
        return restTemplate.getForObject("http://provide/hello",String.class);
    }

    //降级回调
    @Override
    protected String getFallback() {
        //consumer异常捕获getExecutionException()
        return "error"+getExecutionException();
    }
}
