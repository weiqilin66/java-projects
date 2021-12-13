package org.wayne.source.rabbitmq.demo;

import com.rabbitmq.client.Channel;
import org.wayne.source.rabbitmq.Rb00;

public class TestAcknowledgeMode {

    public static void main(String[] args) throws Exception {
        final Channel singleConnectionChannel = Rb00.getSingleConnectionChannel();
//        NONE, 无ack   直接全部消息投向消费者
//                MANUAL,   手动ack
//                AUTO;  自动ack   执行成功ack异常nack
    }
}
