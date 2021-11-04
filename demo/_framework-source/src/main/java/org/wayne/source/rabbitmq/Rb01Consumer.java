package org.wayne.source.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

public class Rb01Consumer extends DefaultConsumer {
    public Rb01Consumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.out.println("监听到消息:"+new String(body));
        final Channel channel = super.getChannel();
        // 显示确认消息
        channel.basicAck(envelope.getDeliveryTag(),false);
    }
}
