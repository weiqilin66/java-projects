package org.wayne.card;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.wayne.common.init.HtConfigSetting;

/**
 * @Description: 二手卡
 * @author: lwq
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class CardServer {
    public static void main(String[] args) {
        new HtConfigSetting();
        SpringApplication.run(CardServer.class, args);

    }
}
