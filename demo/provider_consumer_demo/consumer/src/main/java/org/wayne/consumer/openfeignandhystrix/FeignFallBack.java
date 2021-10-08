package org.wayne.consumer.openfeignandhystrix;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.wayne.consumer.openfeign.FeignService;
import org.wayne.demo.User;

/**
 * @Description: openfeign+hystrix 服务降级方式1
 * 步骤
 * 1. 实现FeignService接口
 * 2. 配置文件开启 feign.hystrix.enabled=true
 *
 * 原理： 访问服务失败则调用本地实现接口的类方法
 * @author: LinWeiQi
 */
@Component
@RequestMapping("/openFeignAndHystrix")  //配置路径只是防止和feign接口中的地址重复
public class FeignFallBack implements FeignService {
    @Override
    public String hello() {
        return "error";
    }

    @Override
    public String hello2(String name) {
        return null;
    }

    @Override
    public User user2(User user) {
        return null;
    }

    @Override
    public User user1(String username, String password) {
        return null;
    }

    @Override
    public String del(Integer id) {
        return null;
    }

    @Override
    public String testTOut(int ms) {
        return "timeout";
    }
}
