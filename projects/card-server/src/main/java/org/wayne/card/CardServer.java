package org.wayne.card;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.wayne.common.init.HtConfigSetting;

/**
 * @Description:
 * @author: lwq
 */
@SpringBootApplication //exclude 只能配置剔除自动配置类,使用ComponentScan
@ComponentScan(basePackages = {
        "org.wayne.card"
        ,"org.wayne.common"
}
        ,excludeFilters = {
        //不注入RedisConfig这个类型的即包含了它的子类,实现类
//        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,value = {RedisConfig.class})
        //FilterType.ANNOTATION ,value={Controller.class} 不注入@controller注解的
}
)
@EnableDiscoveryClient
@EnableFeignClients
public class CardServer {
    public static void main(String[] args) {
//        SpringApplication.run(CardServer.class, args);
        SpringApplication application = new SpringApplication(CardServer.class);
        application.addInitializers(new HtConfigSetting());
        application.run(args);
    }
}
