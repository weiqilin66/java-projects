package org.wayne.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

/**
 * @Description: 消息队列下线服务
 * @author: lwq
 */
@Slf4j
public class RabbitController {

    @Autowired
    private RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry;

    @GetMapping("/rabbit/stop")
    public String stop(){
        rabbitListenerEndpointRegistry.stop();
        log.info("停止MQ监听");
        return "停止MQ监听success";
    }
    @GetMapping("/rabbit/start")
    public String start(){
        rabbitListenerEndpointRegistry.start();
        log.info("启动MQ监听");
        return "启动MQ监听success";
    }

    /**
     * listener那边必须指定id 否者是自动生成的字符串无法定位
     * @param id
     * @return
     */
    @GetMapping("/rabbit/stopOne")
    public String stop2(String id) {
        final Set<String> listenerContainerIds = rabbitListenerEndpointRegistry.getListenerContainerIds();
        if (!listenerContainerIds.contains(id)) {
            return String.format("id:%s 要停止的MQ不存在", id);
        }
        final MessageListenerContainer listenerContainer = rabbitListenerEndpointRegistry.getListenerContainer(id);
        try {
            listenerContainer.stop();
        } catch (Exception e) {
            log.error("消息队列:{},停止异常", id, e);
        }
        log.info("停止MQ监听");
        return "停止MQ监听success";
    }

    @GetMapping("/rabbit/startOne")
    public String start2(String id) {
        final Set<String> listenerContainerIds = rabbitListenerEndpointRegistry.getListenerContainerIds();
        if (!listenerContainerIds.contains(id)) {
            return String.format("id:%s 要启动的MQ不存在", id);
        }
        final MessageListenerContainer listenerContainer = rabbitListenerEndpointRegistry.getListenerContainer(id);
        try {
            listenerContainer.start();
        } catch (Exception e) {
            log.error("消息队列:{},启动监听异常", id, e);
        }
        log.info("启动MQ监听");
        return "启动MQ监听success";
    }
}
