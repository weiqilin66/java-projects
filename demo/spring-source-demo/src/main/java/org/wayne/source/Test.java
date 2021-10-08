package org.wayne.source;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.wayne.getbean.Config;

/**
 * @Description:  spring 源码解析入口
 * @author: lwq
 */
public class Test {
    public static void main(String[] args) {
        // 读取注解
//        AnnotationConfigApplicationContext applicationContext =new AnnotationConfigApplicationContext(Config.class);
        //
        final ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("ecpApp.xml");
        System.out.println("finish");


    }
}

