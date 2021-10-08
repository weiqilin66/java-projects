package com.wayne.rabbitmq.rabbitmqconfig;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: fanout:转发给所有的队列
 * @author: LinWeiQi
 */
@Configuration
public class FanOutConfig {

    @Bean
    Queue queue1(){
        return new Queue("queue1");
    }
    @Bean
    Queue queue2(){
        return new Queue("queue2");
    }

    @Bean
    FanoutExchange fanoutExchange(){
        return new FanoutExchange("fanoutExchange",true,false);
    }

    //2个binding
    @Bean
    Binding fanoutBinding1(){
        return BindingBuilder.bind(queue1()).to(fanoutExchange());
    }
    @Bean
    Binding fanoutBinding2(){
        return BindingBuilder.bind(queue2()).to(fanoutExchange());
    }

}
