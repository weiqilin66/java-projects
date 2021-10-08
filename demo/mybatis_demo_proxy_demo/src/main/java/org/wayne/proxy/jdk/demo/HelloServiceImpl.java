package org.wayne.proxy.jdk.demo;

/**
 * @Description: 真实服务对象
 * @author: lwq
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public void sayHello(String msg) {
        System.out.printf("hello:%s\n",msg);
    }
}
