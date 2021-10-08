package org.wayne.mythread.base.c_priority;


import org.wayne.mythread.base.a_thread.MyThread1;

/**
 * @Description: priority继承性 随机性
 * 优先级1-10 越大优先级越高
 * 优先级不代表绝对的先后循序, 代表优先运行的概率高
 * @author: LinWeiQi
 */
public class TestPriority extends Thread {
    public static void main(String[] args) {
        System.out.println("start ---"+Thread.currentThread().getPriority());
        Thread.currentThread().setPriority(6);
        System.out.println("end ---" +Thread.currentThread().getPriority()+ "---src/main" +Thread.currentThread().getName());
        System.out.println(Thread.currentThread().getId());
        MyThread1 t1 = new MyThread1();
        t1.start();
        System.out.println(t1.getName());;


    }

}
