package org.wayne.mythread.base.a_thread;

/**
 * @Description: 继承Thread 实现Runnable 来定义线程类
 * Java的多继承特性,所以推荐用实现Runnable,new Thread().start方法启动线程
 * @author: LinWeiQi
 */
public class MyThread1 extends Thread {
    @Override
    public void run() {
        super.run();
        System.out.println("MyThread1 run priority=" + this.getPriority());
        MyThread2 thread2 = new MyThread2();
        thread2.start();
    }
}
