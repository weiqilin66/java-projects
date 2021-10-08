package org.wayne.getbean;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Description:
 * @Node: 测试结论: getBean(class) 返回的是对应类的实例 get(interface)返回接口的实现类 ,多个实现类时报错 instanceof Tom是Tom也是User
 * @author: lwq
 */
public class Test {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext =new AnnotationConfigApplicationContext(Config.class);

        final User bean2 = applicationContext.getBean(Tom.class);
        final User bean3 = applicationContext.getBean(Jerry.class);
        final User bean = applicationContext.getBean(User.class);

    }
}

