package com.lambda;


/**
 * @Description: 在Lambda表达式使用中，Lambda表达式外面的局部变量会被JVM隐式的编译成final类型，Lambda表达式内部只能访问，不能修改
 * Lambda表达式内部对静态变量和成员变量是可读可写的
 * Lambda不能访问函数接口的默认方法，在函数接口中可以添加default关键字定义默认的方法
 * @author: LinWeiQi
 */
public class LambdaDemo {

    public static void main(String[] args) {
        // 局部变量对于Lambda表达式来说是静态的 无法修改只能访问
        int x = 1;

        // lambda写法
        Sum sum = ( int a,int c)-> a + c;
        // 类型可省略
        sum = (  a, c)-> a + c;
        // 方法引用 A::B 引用Integer类中的sum方法
        sum=Integer::sum;
        //Lambda表达式函数调用
        System.out.println(sum.add(1, 5));
        OneParam oneParam = name-> name;
        System.out.println(oneParam.one("one"));
        System.out.println(sum.add(1,3));

    }


    /**
     * 函数式接口
     */
    @FunctionalInterface
    interface Sum {
        int add(int a,int b);
    }

    // @FunctionalInterface 可省略
    interface OneParam{
        String one(String s);
    }

}