package org.wayne.mq.demo.client;

import org.wayne.mq.demo.server.BrokerServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @Description: 客户端类
 * @author: lwq
 */
public class MqClient {

    /**
     * 生产消息
     */
    public static void produce(String msg) throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), BrokerServer.SERVER_PORT);

        try (PrintWriter out = new PrintWriter(socket.getOutputStream())) {
            out.println(msg);
            out.flush();
        }
    }

    /**
     * 消费消息
     */
    public static String consumer() throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), BrokerServer.SERVER_PORT);
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream())
        ) {
            //先向消息队列发送consumer表示消费
            out.println("consumer");
            out.flush();
            // 再从消息队列获取一条消息
            String msg = in.readLine();
            return msg;
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("mq客户端启动");
        System.out.println("发送消息到消息处理中心");
        MqClient.produce("hello world");

        Thread.sleep(1000);
        System.out.println("准备消费2条消息");
        MqClient.consumer();
        MqClient.consumer();
    }
}
