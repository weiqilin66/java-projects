#### Rb00~04 基础源码级应用 
1. 工厂连接,获取信道
2. 发送: 事务/异步确认
3. 监听: 手动自动确认

##### Rb05 boot+rabbit的源码
1. 有一个BeanPostProcessor来处理这个注解，把注解相关的内容取出来，封装成一个RabbitListenerEndPoint。
2. 然后给每个Endpoint创建一个MessageListenerContainer，在这个container中注册一个MessageListener，在这个MessageListener中创建
   了一个HandlerAdapter，这个adapter与rabbitmq broker建立一个connection，接收rabbitmq broker push过来的message，
   放到一个blocking queue中。至此完成消息的接收。
3. 接下来是消息的处理。上文的adapter把我们用@RabbitListener注解的普通方法通过反射的方式还原出来，
   从blocking queue中poll出一个一个的message，进行处理。
 