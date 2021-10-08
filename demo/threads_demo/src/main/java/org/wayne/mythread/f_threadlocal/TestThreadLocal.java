package org.wayne.mythread.f_threadlocal;

/**
 * @Description: 变量值的共享可以使用public static变量的形式，所有线程都使用一个public static变量。
 * 如果想实现每一个线程都有自己的共享变量该如何解决 ==> ThreadLocal
 * 实现每一个线程都有自己的共享变量 --隔离性
 * @author: LinWeiQi
 */
public class TestThreadLocal {

    public static ThreadLocal t1 = new ThreadLocal<String>();
    public ThreadLocal t2 = new ThreadLocal<String>();


    public static void main(String[] args) {
        System.out.println(t1.get());//null
        t1.set("aaa");
        System.out.println(t1.get());//aaa
        t1.remove();
        System.out.println(t1.get());//null

        System.out.println(new TestThreadLocal().y.get());
        System.out.println(x.get());
    }
    //解决初始值为null的问题
    public static ThreadLocal2initialValue x = new ThreadLocal2initialValue();
    public  ThreadLocal2initialValue y = new ThreadLocal2initialValue();
    //继承ThreadLocal重写initialValue方法
    static public class ThreadLocal2initialValue extends ThreadLocal {
        @Override
        protected Object initialValue() {
            return "我是默认值 第一次get不再为null";
        }
    }

}
