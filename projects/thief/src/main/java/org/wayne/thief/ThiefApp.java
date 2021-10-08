package org.wayne.thief;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.wayne.common.init.redis.RedisConfig;
import org.wayne.common.init.HtConfigSetting;

/**
 * @Description: 爬
 * @author: lwq
 */
@SpringBootApplication //exclude 只能配置剔除自动配置类,使用ComponentScan
@ComponentScan(basePackages = {
        "org.wayne.thief"
        ,"org.wayne.common"
},excludeFilters = {
        //不注入RedisConfig这个类型的即包含了它的子类,实现类
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,value = {RedisConfig.class})
        //FilterType.ANNOTATION ,value={Controller.class} 不注入@controller注解的
})
@EnableScheduling
@EnableDiscoveryClient
public class ThiefApp {
    public static void main(String[] args) {
        //        SpringApplication.run(ThiefApp.class, args);
        SpringApplication application = new SpringApplication(ThiefApp.class);
        application.addInitializers(new HtConfigSetting());
        application.run(args);
    }
}
