package org.wayne.mythread.g_lock._readwritelock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Description: 我们刚刚接触到的ReentrantLock（排他锁）具有完全互斥排他的效果，即同一时刻只允许一个线程访问，
 * 这样做虽然虽然保证了实例变量的线程安全性，但效率非常低下。ReadWriteLock接口的实现类-ReentrantReadWriteLock读写锁就是为了解决这个问题。
 *
1. 读读共享
两个线程同时运行read方法，你会发现两个线程可以同时或者说是几乎同时运行lock()方法后面的代码，输出的两句话显示的时间一样。这样提高了程序的运行效率
lock.readLock().lock();//读锁
lock.writeLock().lock();//写锁
2. 写写互斥
3. 读写互斥
 */
public class TestReentrantReadWriteLock {

    public static void main(String[] args) throws InterruptedException {
        TestReentrantReadWriteLock t = new TestReentrantReadWriteLock();
        Runnable runnable1 = new Runnable() {//快速定义起线程写法
            @Override
            public void run() {
                t.read();
            }
        };
        // 读读共享
        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable1);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("读写测试");
        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                t.write();
            }
        };
        //写读 写写互斥
        Thread writeThread = new Thread(runnable2);
        writeThread.start();
        Thread writeThread2 = new Thread(runnable2);
        writeThread2.start();
        Thread readThread = new Thread(runnable1);
        readThread.start();


    }

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock unFairLock = new ReentrantReadWriteLock(false); //非公平锁 吞吐量上升

    //读读共享
    public void read() {
        try {
            try {
                lock.readLock().lock();//读锁
                System.out.println("获得读锁" + Thread.currentThread().getName()
                        + " " + System.currentTimeMillis());
                Thread.sleep(5000);
            } finally {
                lock.readLock().unlock();
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void write() {
        try {
            try {
                lock.writeLock().lock();//写锁
                System.out.println("获得写锁" + Thread.currentThread().getName()
                        + " " + System.currentTimeMillis());
                Thread.sleep(5000);
            } finally {
                lock.writeLock().unlock();
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
