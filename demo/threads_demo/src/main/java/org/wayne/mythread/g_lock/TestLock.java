package org.wayne.mythread.g_lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 虽然synchronized方法和语句的范围机制使得使用监视器锁更容易编程，并且有助于避免涉及锁的许多常见编程错误，但是有时您需要以更灵活的方式处理锁。
 例如，用于遍历并发访问的数据结构的一些算法需要使用“手动”或“链锁定”：获取节点A的锁定，然后获取节点B，然后释放A并获取C，然后释放B并获得D等。
 在这种场景中synchronized关键字就不那么容易实现了，使用Lock接口容易很多。

 死锁
 永远在互相等待的进程称为死锁进程。
 根据《Java并发编程的艺术》有下面四种避免死锁的常见方法：1线程1锁1资源,定时锁

 避免一个线程同时获得多个锁
 避免一个线程在锁内同时占用多个资源，尽量保证每个锁只占用一个资源
 尝试使用定时锁，使用lock.tryLock(timeout)来替代使用内部锁机制
 对于数据库锁，加锁和解锁必须在一个数据库连接里，否则会出现解锁失败的情况

 */
public class TestLock {
    public static void main(String[] args) {
        //当一个线程运行完毕后才把锁释放，其他线程才能执行，其他线程的执行顺序是不确定的
        MyService service = new MyService();

        Thread a1 = new Thread(service);
        Thread a2 = new Thread(service);
        Thread a3 = new Thread(service);
        Thread a4 = new Thread(service);
        Thread a5 = new Thread(service);

        a1.start();
        a2.start();
        a3.start();
        a4.start();
        a5.start();
//        System.out.println(Thread.currentThread().getName()+":测试lock是否对象锁");//结论 和synchronized一样为对象锁
//        MyService service2 = new MyService();
//        Thread a6 = new Thread(service2);
//        a6.start();
    }

    static public class MyService extends Thread{

        private Lock lock = new ReentrantLock();//创建锁

        @Override
        public void run() {
            lock.lock(); //获得锁 如果锁不可用，则当前线程将被禁用以进行线程调度，并处于休眠状态，直到获取锁
            try {
                for (int i = 0; i < 5; i++) {
                    System.out.println("ThreadName=" + Thread.currentThread().getName() + (" " + (i + 1)));
                }
            } finally {
                lock.unlock();// 释放锁
            }
        }
    }


}
