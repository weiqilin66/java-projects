package org.wayne.source.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

// 发布消息 消费消息
public class Rb01 {

    public static void main(String[] args) throws Exception {
        final Channel singleConnectionChannel = Rb00.getSingleConnectionChannel();
        provider(singleConnectionChannel);
        System.out.println("投递完毕");
        Thread.sleep(10000);
        consumer(singleConnectionChannel);
        System.out.println("消费完毕");

        Rb00.getConnection().close();
    }

    private static void provider(Channel channel) throws IOException {
        final byte[] bytes = "hello world".getBytes(StandardCharsets.UTF_8);
        // 发送
        channel.basicPublish("46-paph-credit-ex","46-paph-credit-router",
                new AMQP.BasicProperties.Builder().
                        contentType("applicaiton/json;charset=utf-8")
                        .priority(1)//优先级
                        .deliveryMode(2) // 投递模式: 2是持久化
                        .expiration("60000") //60s过期
                        .build()
                ,bytes);
    }

    private static void consumer(Channel channel) throws IOException {
        boolean autoAck = false;
        String consumerTag = "c1";
        channel.basicConsume("46-paph-credit-queue",autoAck,consumerTag,new Rb01Consumer(channel));
    }
}
