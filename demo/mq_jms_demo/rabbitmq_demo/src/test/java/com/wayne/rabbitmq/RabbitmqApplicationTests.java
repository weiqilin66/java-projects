package com.wayne.rabbitmq;

import com.wayne.rabbitmq.rabbitmqconfig.TopicConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

@SpringBootTest
class RabbitmqApplicationTests {

    @Autowired
    RabbitTemplate rabbitTemplate;
    @Test
    void contextLoads() {
        //1.没指定交换器,使用默认exchange , routingKey为队列名字
        rabbitTemplate.convertAndSend("myQ","默认exchange发送的消息内容");



        //2 directExchange搭配routingKey
        rabbitTemplate.convertAndSend("exchange1","direct","指定转换器和routingKey发送的消息内容");





        //3 fanout
        rabbitTemplate.convertAndSend("fanout",null,"hello,fanout");
        //4 topic
        rabbitTemplate.convertAndSend(TopicConfig.TOPEXCHANGE,"xiaomi.xxxx","匹配到小米队列");
        rabbitTemplate.convertAndSend(TopicConfig.TOPEXCHANGE,"xiaomi.phone","匹配到phone队列和xiaomi队列");
    }
    @Autowired
    Environment env;
    @Test
    void t2(){
        System.out.println(env.getProperty("spring.rabbitmq.listener.concurrency",int.class));
    }

}

