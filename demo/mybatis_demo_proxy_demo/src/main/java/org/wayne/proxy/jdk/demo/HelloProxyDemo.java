package org.wayne.proxy.jdk.demo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Description: JDK代理范例,实现java.lang.reflect.InvocationHandler
 * @author: lwq
 */
public class HelloProxyDemo implements InvocationHandler {

    // 真实服务对象
    private Object target;

    /**
     * 绑定委托对象并返回代理类
     * @param target
     * @return
     */
    public Object bind(Object target) {
        this.target = target;
        // 取得代理对象
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),this);
    }

    /**
     * 回调方法
     @Description 通过代理对象调用方法,会进入这个方法
     @参数 proxy代理对象 method调用的方法 args方法的参数
     * @return
     * @throws Throwable
     */

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("I am JDK Proxy");
        Object res;
        // ...可在调用真实服务之前执行其他逻辑
        System.out.println("真实服务调用前逻辑");
        // 执行等于调用了HelloServiceImpl的sayHello方法
        res = method.invoke(target,args);
        System.out.println("调用后逻辑");

        return res;
    }


    public static void main(String[] args) {
        HelloProxyDemo helloProxyDemo =new HelloProxyDemo();
        final HelloService proxy = (HelloService)helloProxyDemo.bind(new HelloServiceImpl());
        proxy.sayHello("world");
    }






}