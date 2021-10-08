package org.wayne.mq.demo.server;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Description: 消息处理中心 负责消息{ 接收 存储 转发 }
 * @author: lwq
 */
public class BrokerQ {
    /**
     * 最大消息数
     */
    private final static int MAX_SIZE = 10;

    /**
     * 消息队列
     */
    private static ArrayBlockingQueue<String> messageQueue = new ArrayBlockingQueue<>(MAX_SIZE);

    /**
     * 接收生产者消息
     */
    public static void produce(String msg){
        if (messageQueue.offer(msg)) {
            System.out.println("成功向消息处理中心投递消息: "+ msg + "当前暂存消息数: "+ messageQueue.size());
        }else {
            System.out.println("消息处理中心暂存消息达到最大,无法接收新消息");
        }
    }

    /**
     * 转发消息给消费者
     */
    public static String consumer(){
        String msg = messageQueue.poll();
        if (msg!=null) {
            System.out.println("当前消费的消息: " + msg + "剩余消息数量: " + messageQueue.size());
        }else {
            System.out.println("队列中没有消息了");
        }
        System.out.println("---");
        return msg;
    }

    /**
     * @Description ArrayBlockingQueue 介绍
     * offer(E e)：表示如果可能的话，将 e 加到 BlockingQueue 里，即如果 BlockingQueue 可以容纳，则返回 true，否则返回 false
     * poll(time)：取走 BlockingQueue 里排在首位的对象，若不能立即取出，则可以等 time 参数规定的时间,取不到时返回 null
     */
}
