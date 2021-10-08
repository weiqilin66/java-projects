package org.wayne.mythread.g_lock._reentrantlock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description: 可重入锁/排他锁
 * 在使用notify/notifyAll()方法进行通知时，被通知的线程是有JVM选择的，使用ReentrantLock类结合Condition实例可以实现“选择性通知”，
 * 这个功能非常重要，而且是Condition接口默认提供的。
 * 在使用wait/notify实现等待通知机制的时候我们知道必须执行完notify()方法所在的synchronized代码块后才释放锁。在这里也差不多
 * ，必须执行完signal所在的try语句块之后才释放锁，condition.await()后的语句才能被执行。
 * @author: LinWeiQi
 */
public class TestCondition {
    static public class MyserviceMoreCondition {

        private Lock lock = new ReentrantLock();//可重入锁
        public Condition conditionA = lock.newCondition();
        public Condition conditionB = lock.newCondition();

        public void awaitA() {
            lock.lock();
            try {
                System.out.println("begin awaitA时间为" + System.currentTimeMillis()
                        + " ThreadName=" + Thread.currentThread().getName());
                conditionA.await();// 已a条件等待  conditionA.signal 唤醒这个等待条件的线程
                System.out.println("  end awaitA时间为" + System.currentTimeMillis()
                        + " ThreadName=" + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void awaitB() {
            lock.lock();
            try {
                System.out.println("begin awaitB时间为" + System.currentTimeMillis()
                        + " ThreadName=" + Thread.currentThread().getName());
                conditionB.await();
                System.out.println("  end awaitB时间为" + System.currentTimeMillis()
                        + " ThreadName=" + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void signalAll_A() {
            lock.lock();
            try {
                System.out.println("  signalAll_A时间为" + System.currentTimeMillis()
                        + " ThreadName=" + Thread.currentThread().getName());
                conditionA.signalAll();
            } finally {
                lock.unlock();
            }
        }

        public void signalAll_B() {
            lock.lock();
            try {
                System.out.println("  signalAll_B时间为" + System.currentTimeMillis()
                        + " ThreadName=" + Thread.currentThread().getName());
                conditionB.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {

        MyserviceMoreCondition service = new MyserviceMoreCondition();

        ThreadA a = new ThreadA(service);
        a.setName("A");
        a.start();

        ThreadB b = new ThreadB(service);
        b.setName("B");
        b.start();

        Thread.sleep(3000);

        service.signalAll_A();

    }
    static public class ThreadA extends Thread {

        private MyserviceMoreCondition service;

        public ThreadA(MyserviceMoreCondition service) {
            super();
            this.service = service;
        }

        @Override
        public void run() {
            service.awaitA();
        }
    }
    static public class ThreadB extends Thread {

        private MyserviceMoreCondition service;

        public ThreadB(MyserviceMoreCondition service) {
            super();
            this.service = service;
        }

        @Override
        public void run() {
            service.awaitB();
        }
    }

}
