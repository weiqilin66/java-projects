package org.wayne.jmsdemo;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;


/**
 * @Description:  ActiveMq 如何通过JMS完成消息队列功能
 * 消息队列提供商 实现了一系列JMS的接口
 * 底层如何实现 在使用JMS连接使用MQ使用无需知道
 * @author: lwq
 */
public class ConsumerDemo {
    public static final String USER_NAME = ActiveMQConnection.DEFAULT_USER;
    public static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    public static final String URL = ActiveMQConnection.DEFAULT_BROKER_URL;

    public static void main(String[] args) throws JMSException {
        // 创建连接
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(USER_NAME,PASSWORD,URL);
        Session session = null;
        try {
            final Connection connection = connectionFactory.createConnection();
            // 创建会话
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            // 创建队列(生产消费目的地)
            final Queue queue = session.createQueue("mq_test1");
            // 生产者
            final MessageProducer producer = session.createProducer(queue);
            final TextMessage textMessage = session.createTextMessage("发送一条消息");
            producer.send(textMessage);
            session.commit();
            Thread.sleep(1000);
            // 消费者
            final MessageConsumer consumer = session.createConsumer(queue);
            // 实现消息监听
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    TextMessage textMessage = (TextMessage) message;
                    System.out.println("监听到的消息:" + textMessage);
                }
            });
            session.commit();




        } catch (JMSException | InterruptedException e) {
            e.printStackTrace();
        }finally {
            session.close();
        }
    }


}


