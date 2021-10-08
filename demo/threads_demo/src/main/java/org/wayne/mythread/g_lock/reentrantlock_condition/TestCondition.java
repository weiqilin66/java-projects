package org.wayne.mythread.g_lock.reentrantlock_condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description: synchronized关键字与wait()和notify/notifyAll()方法相结合可以实现等待/通知机制，
 * ReentrantLock借助于Condition接口与newCondition() 方法实现
 *
 * 在使用wait/notify实现等待通知机制的时候我们知道必须执行完notify()方法所在的synchronized代码块后才释放锁。在这里也差不多，
 * 必须执行完signal所在的try语句块之后才释放锁，condition.await()后的语句才能被执行
 *
 * 优势: 在使用notify/notifyAll()方法进行通知时，被通知的线程是有JVM选择的，使用ReentrantLock类结合Condition实例可以实现“选择性通知
 *
 * void await()	相当于Object类的wait方法
 * signal()	相当于Object类的notify方法
 * signalAll()	相当于Object类的notifyAll方法
 * @author: LinWeiQi
 */
public class TestCondition {

    public static void main(String[] args) throws InterruptedException {

        MyService service = new MyService();

        ThreadA a = new ThreadA(service);
        a.start();

        Thread.sleep(3000);

        service.signal();

    }

    static public class MyService {

        private Lock lock = new ReentrantLock();
        public Condition condition = lock.newCondition();//借助condition的await/signal...实现等待通知

        public void await() {
            lock.lock();//获得锁,获取不到阻塞
            try {
                System.out.println(" await时间为" + System.currentTimeMillis());
                condition.await();
                System.out.println("这是condition.await()方法之后的语句，condition.signal()方法之后我才被执行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void signal() throws InterruptedException {
            lock.lock();
            try {
                System.out.println("signal时间为" + System.currentTimeMillis());
                condition.signal();
                Thread.sleep(3000);
                System.out.println("这是condition.signal()方法之后的语句");
            } finally {
                lock.unlock();
            }
        }
    }

    static public class ThreadA extends Thread {

        private MyService service;

        public ThreadA(MyService service) {
            super();
            this.service = service;
        }

        @Override
        public void run() {
            service.await();
        }
    }
}
