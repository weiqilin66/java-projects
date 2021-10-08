package org.wayne.swagger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.wayne.common.init.redis.RedisConfig;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @Description:
 * @author: lwq
 */
@SpringBootApplication //exclude 只能配置剔除自动配置类,使用ComponentScan
@ComponentScan(basePackages = {
        "org.wayne.swagger"
        ,"org.wayne.common"
},excludeFilters = {
        //不注入RedisConfig这个类型的即包含了它的子类,实现类
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,value = {RedisConfig.class})
        //FilterType.ANNOTATION ,value={Controller.class} 不注入@controller注解的
})
@EnableOpenApi
@EnableDiscoveryClient
public class SwagApp {
    public static void main(String[] args) {
        SpringApplication.run(SwagApp.class,args);
    }
}
