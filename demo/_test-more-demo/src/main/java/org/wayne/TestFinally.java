package org.wayne;

/**
 * @Description: finally如果抛出异常程序还会继续往下执行么?-->不会
 * @author: lwq
 */
public class TestFinally {

    public static void t1(){
        try {
            System.out.println("i am try");
            int i = 1;
            int b = i/0;
        }catch (Exception e){
            System.out.println("i am catch");

        }finally {
            System.out.println("enter  finally");
            int i = 1;
            int b = i/0;
        }

    }

    public static void main(String[] args) {
        t1();
        System.out.println("finally如果抛出异常程序还会继续往下执行");
    }

}
