package org.wayne.source.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

// 发送者确认 本例为串行确认 , 执行命令basic.publish basic.ack 对比事务为publish,commit,commit-ok 效率差不多
// 优化 异步生产者确认转Rb04 批量确认(如果经常丢失,效率反而降低,且有一条丢失无法确认,得批量重发)
public class Rb03 {

    public static void main(String[] args) throws Exception {

        final Channel channel = Rb00.getSingleConnectionChannel();
        channel.confirmSelect();// 信道设置publisher confirm

        channel.basicPublish("46-paph-credit-ex", "46-paph-credit-router",
                new AMQP.BasicProperties.Builder().
                        contentType("applicaiton/json;charset=utf-8")
                        .priority(1)//优先级
                        .deliveryMode(2) // 投递模式: 2是持久化
                        .expiration("60000") //60s过期
                        .build()
                , "publisher confirm test".getBytes(StandardCharsets.UTF_8)
        );
        try {
            if (!channel.waitForConfirms(100)) { //可配参数是超时时间 ,这个方法会阻塞到消息返回,或者抛出超时异常,不配置超时时间会一直阻塞
                System.out.println("消息发送失败");
                // 进行重试发送等
            }else {
                System.out.println("line30:broker接收消息成功");
            }
        }catch (TimeoutException e){
            System.out.println("接收消息发布ack/nack超时,无法确定消息是否到达broker服务器");
            System.out.print(e);
        }

    }
}
