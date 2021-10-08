package com.wayne.rabbitmq.rabbitmqconfig;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: provide->exchange+binding->queue->consumer
 * 队列和交换器使用之前,必须先声明他们 (与activemq不同,不需要先声明队列)
 * @author: LinWeiQi
 */
@Configuration
public class DirectConfig {
    public static final String directName = "exchange1";

    //声明消息队列
    @Bean
    Queue queue(){
        return new Queue("myQ");
    }

    //durable持久化发送 autoDelete长期不使用是否删除
    //exchange交换器
    @Bean
    DirectExchange directExchange(){
        return new DirectExchange(directName,true,false);
    }

    //ruler1 为绑定键 消息发送者发送到exchange1交换器 且路由键值为ruler1 才会发送到queue()注册的队列
    @Bean
    Binding binding(){
        return BindingBuilder.bind(queue()).to(directExchange()).with("ruler1");
    }
}
