package org.wayne.jvm.demo.problem;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 模拟方法区oom
 * VM Args：-XX:PermSize=10M -XX:MaxPermSize=10M   jdk7配置方法区大小
 * jdk8 元空间替代了永久代 ,一般不会出现方法区内存溢出
 */
public class JavaMethodAreaOOM {
    public static void main(String[] args) {
        while (true) {
            // CGLib 增强方法 -->方法区需要保存更多的类型信息,模拟内存溢出
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                    return proxy.invokeSuper(obj, args);
                }
            });
            enhancer.create();
        }
    }

    static class OOMObject {
    }
}