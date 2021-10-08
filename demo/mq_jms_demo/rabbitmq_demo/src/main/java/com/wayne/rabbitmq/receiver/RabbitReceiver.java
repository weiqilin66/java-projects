package com.wayne.rabbitmq.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @author: LinWeiQi
 */
@Component
public class RabbitReceiver {
    // 指定ListenerContainer
    @RabbitListener(queues = "myQ" ,containerFactory = "multiListenerContainer")
    public void received(String msg){//队列中的消息
        System.out.println("receiver1:"+msg);
    }

    //--------------------------- fanout ----------------------------------------

    @RabbitListener(queues = "queue1")//监听哪个队列
    public void received2(String msg){//队列中的消息
        System.out.println("receiver2:"+msg);
    }

    @RabbitListener(queues = "queue2")//监听哪个队列
    public void received3(String msg){//队列中的消息
        System.out.println("receiver3:"+msg);
    }

    //---------------------------Topic---------------------------------------
    @RabbitListener(queues = "xiaomi")//监听哪个队列
    public void received4(String msg){//队列中的消息
        System.out.println("receiver4:"+msg);
    }
    @RabbitListener(queues = "phone")//监听哪个队列
    public void received5(String msg){//队列中的消息
        System.out.println("receiver5:"+msg);
    }
}
