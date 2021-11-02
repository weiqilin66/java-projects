package org.wayne.source.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Description:  spring 源码解析入口
 * @author: lwq
 */
public class MainTest {
    public static void main(String[] args) {
        // 读取注解
//        AnnotationConfigApplicationContext applicationContext =new AnnotationConfigApplicationContext(Config.class);
        //
        final ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("ecpApp.xml");
        System.out.println("finish");


    }
}

