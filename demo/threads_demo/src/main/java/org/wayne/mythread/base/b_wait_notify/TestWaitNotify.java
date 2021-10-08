package org.wayne.mythread.base.b_wait_notify;

/**
 * @Description: 等待/通知机制，
 * 是指一个线程A调用了对象O的wait()方法进入等待状态，而另一个线程B调用了对象O的notify()/notifyAll()方法，
 * 线程A收到通知后退出等待队列，进入可运行状态，进而执行后续操作
 * @author: LinWeiQi
 */
public class TestWaitNotify {
    public static void main(String[] args) {

        try {
            Object object = new Object();

            ThreadA a = new ThreadA(object);
            a.start();

            Thread.sleep(50);

            ThreadB b = new ThreadB(object);
            b.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
