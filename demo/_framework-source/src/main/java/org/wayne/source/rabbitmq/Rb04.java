package org.wayne.source.rabbitmq;


// 异步生产者确认

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.SortedSet;
import java.util.TreeSet;


public class Rb04 {

    public static void main(String[] args) throws Exception {
        final Channel channel = Rb00.getSingleConnectionChannel();
        //维护消息序号集合confirmSet
        SortedSet<Long> confirmSet = new TreeSet();
        // 信道设置publisher confirm
        channel.confirmSelect();
        // 模拟投递2次
        for (int i = 0; i < 2; i++) {
            final long nextPublishSeqNo = channel.getNextPublishSeqNo();
            System.out.println("消息标识nextPublishSeqNo: " + nextPublishSeqNo);
            channel.basicPublish("46-paph-credit-ex", "46-paph-credit-router",
                    new AMQP.BasicProperties.Builder().
                            contentType("applicaiton/json;charset=utf-8")
                            .priority(1)//优先级
                            .deliveryMode(2) // 投递模式: 2是持久化
                            .expiration("60000") //60s过期
                            .build()
                    , "publisher confirm test".getBytes(StandardCharsets.UTF_8)
            );
            confirmSet.add(nextPublishSeqNo);
        }



        channel.addConfirmListener(new ConfirmListener() {
            // ack回调
            @Override //deliveryTag 消息唯一标识
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("ack, SeqNo :" + deliveryTag + ", multiple :" + multiple);
                if (multiple) {
//                    confirmSet.headset(deliveryTag - 1).clear();
                } else {
                    System.out.println("确认前confirmSet:"+ confirmSet);
                    confirmSet.remove(deliveryTag);
                    System.out.println("确认后confirmSet:"+ confirmSet);
                }
            }

            //unack回调
            @Override
            public void handleNack(long deliveryTag, boolean b) throws IOException {
                // 重发逻辑 一次维持一个信道情况下 deliveryTag作为消息的标识在数据库找到对应的消息内容
            }
        });
    }
}
