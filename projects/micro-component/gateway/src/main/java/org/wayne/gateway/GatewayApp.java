package org.wayne.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.wayne.common.init.HtConfigSetting;

/**
 * @Description:  gateway
 * @author: LinWeiQi
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApp {
    public static void main(String[] args) {
        //        SpringApplication.run(GatewayApp.class, args);
        SpringApplication application = new SpringApplication(GatewayApp.class);
        application.addInitializers(new HtConfigSetting());
        application.run(args);
    }


}
