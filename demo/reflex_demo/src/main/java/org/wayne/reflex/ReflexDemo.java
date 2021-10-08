package org.wayne.reflex;
import java.lang.reflect.Method;

/**
 * @Description:  反射demo
 * @author: lwq
 */
public class ReflexDemo {
    /**
     *
     * @param beanName 实例名
     * @param openDay 入参
     * @return
     */
    public String reflex(String beanName, String openDay) {
        try {

//            final Object obj = BeanUtilsQ.getBean(beanName);
            final Object obj = new Object();

            Class<?> clazz = obj.getClass();
            final Method[] declaredMethods = clazz.getDeclaredMethods();
            for (Method method : declaredMethods) {
                if ("doExecute".equals(method.getName())) {
                    method.invoke(obj,openDay);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "Success";
    }
}
