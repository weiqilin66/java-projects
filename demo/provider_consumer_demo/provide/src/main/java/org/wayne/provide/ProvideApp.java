package org.wayne.provide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Description: 服务示例
 * @author: LinWeiQi
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ProvideApp {
    public static void main(String[] args) {
        SpringApplication.run(ProvideApp.class,args);
    }
}
