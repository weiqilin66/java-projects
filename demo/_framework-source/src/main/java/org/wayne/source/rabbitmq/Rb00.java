package org.wayne.source.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

// 底层连接rabbit Broker 服务器
// 获取connection及channel

public class Rb00 {
    private static Connection connection;
    private static final Object obj = new Object();

    public static void main(String[] args) throws Exception {
        final Channel channel = getSingleConnectionChannel();
        final boolean open = channel.isOpen();
        System.out.println("开启状态:" + open);
        connection.close();
    }

    public static Channel getSingleConnectionChannel() throws Exception {
        if (connection == null) {
            synchronized (obj) {
                if (connection == null) { // 双重检查锁
                    ConnectionFactory connectionFactory = new ConnectionFactory();
                    connectionFactory.setUsername("admin");
                    connectionFactory.setPassword("admin");
                    connectionFactory.setHost("172.16.45.80");
                    connectionFactory.setPort(5672);
                    connection = connectionFactory.newConnection();
                    System.out.println("连接成功");
                }
            }
        }
        // 创建信道
        return connection.createChannel();

    }
    public static Connection getConnection(){
        return connection;
    }
}
