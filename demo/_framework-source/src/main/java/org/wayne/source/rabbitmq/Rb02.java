package org.wayne.source.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import java.util.HashMap;
import java.util.Map;

// 消息过期时间 队列过期时间 x-expires 过期删除队列(非即时)
// x-message-ttl如果设置为 0 则替换部分immediate功能, 当没有消费者监听该队列时丢弃消息

public class Rb02 {

    @Bean //全局配置消息过期时间
    Queue delayQueueCredit() {
        Map<String, Object> args = new HashMap(3);
        args.put("x-dead-letter-exchange", "credit.delay.consumer.ex");
        args.put("x-dead-letter-routing-key", "credit.delay.consumer.router");
        args.put("x-message-ttl", (Integer.valueOf("credit.delay.ttl")));
        return QueueBuilder.durable("credit.delay.queue").withArguments(args).build();
    }
}
