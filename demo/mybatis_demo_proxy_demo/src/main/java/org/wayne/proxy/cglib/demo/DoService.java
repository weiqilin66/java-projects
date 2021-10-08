package org.wayne.proxy.cglib.demo;

/**
 * @Description: 被代理的对象 不用实现某个接口
 * @author: lwq
 */
public class DoService {
    public void justDoIt(String thing){
        System.out.println("do(不需要借[接]口) "+ thing);
    }
}
