package org.wayne.consumer.openfeignandhystrix;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import org.wayne.consumer.openfeign.FeignService;
import org.wayne.demo.User;

/**
 * @Description: openfeign+hystrix 服务降级方式2
 *
 * 步骤
 * 1. 实现接口 feign.hystrix.FallbackFactory<HelloService>
 * 2. 配置文件开启 feign.hystrix.enabled=true
 *
 * @author: LinWeiQi
 */
@Component
public class IFallBackFactory implements FallbackFactory<FeignService> {
    @Override
    public FeignService create(Throwable throwable) {
        return new FeignService() {
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
        };
    }
}
