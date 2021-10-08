package org.wayne.proxy.cglib.demo;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Description: CGLIB代理范例, 实现 org.springframework.cglib.proxy.MethodInterceptor
 * 不需要接口 可以代理任何类
 * @author: lwq
 */
public class CglibProxyDemo implements MethodInterceptor {

    // 真实服务对象
    private Object target;

    // 创建代理对象(绑定对象)
    public Object getInstance(Object target){
        this.target = target;
        Enhancer enhancer = new Enhancer();

        enhancer.setSuperclass(this.target.getClass());
        enhancer.setCallback(this);
        // 创建代理对象
        return enhancer.create();
    }

    /**
     * 回调方法
     * @param obj
     * @param method
     * @param args
     * @param methodProxy
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("I am CGLIB proxy");
        System.out.println("可执行其他逻辑1");
        final Object resObj = methodProxy.invokeSuper(obj, args);
        System.out.println("可执行其他逻辑2");

        return resObj;
    }

    public static void main(String[] args) {
        CglibProxyDemo cglibProxyDemo = new CglibProxyDemo();
        final DoService obj = (DoService) cglibProxyDemo.getInstance(new DoService());
        obj.justDoIt("CGL");
    }
}
