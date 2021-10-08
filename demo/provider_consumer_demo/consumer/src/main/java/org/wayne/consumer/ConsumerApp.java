package org.wayne.consumer;

import feign.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @Description: 消费者示例
 * @author: LinWeiQi
 */
//@SpringBootApplication
//@EnableDiscoveryClient
//@EnableCircuitBreaker //开启断路器 (容错/服务降级/熔断)
@SpringCloudApplication //等于上面3个注解
@EnableFeignClients //开启feign
public class ConsumerApp {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApp.class,args);
    }

    @Bean
    @LoadBalanced //resTemplate开启负载均衡
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean//无负债均衡restTemplate
    RestTemplate restTemplateOne(){
        return new RestTemplate();
    }

    //feign日志级别( basic/headers/full) 配置
    //配置文件中配置哪些包内日志生效
    @Bean
    Logger.Level loggerLeverl(){
        return Logger.Level.FULL;
    }

}
