package com.wayne.rabbitmq.rabbitmqconfig;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: routingKey带匹配功能,可1个routingKey匹配多个队列
 * @author: LinWeiQi
 */
@Configuration
public class TopicConfig {

    public static final String TOPEXCHANGE  = "topic";

    @Bean
    Queue xiaomiQueue(){
        return new Queue("xiaomi");
    }

    @Bean
    Queue huaweiQueue(){
        return new Queue("huawei");
    }
    @Bean
    Queue phoneQueue(){
        return new Queue("phone");
    }

    @Bean
    TopicExchange topicExchange(){
        return new TopicExchange(TOPEXCHANGE,true,false);

    }
    @Bean
    Binding xiaomiBinding(){
        //所有xiaomi.开头的都会被匹配
        return BindingBuilder.bind(xiaomiQueue()).to(topicExchange()).with("xiaomi.#");
    }
    @Bean
    Binding phoneBinding(){
        //xiaomi.phone匹配到2个队列
        return BindingBuilder.bind(phoneQueue()).to(topicExchange()).with("#.phone.#");
    }
}

