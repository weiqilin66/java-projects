package org.wayne.mythread.base.a_thread;

/**
 * @Description:
 * @author: LinWeiQi
 */
public class MyThread2 extends Thread {
    @Override
    public void run() {
        System.out.println("MyThread2 run priority=" + this.getPriority());
    }
}
