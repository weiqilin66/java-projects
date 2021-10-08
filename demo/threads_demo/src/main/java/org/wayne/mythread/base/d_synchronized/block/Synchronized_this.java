package org.wayne.mythread.base.d_synchronized.block;

/**
 * @Description: 同步代码块 当一个同步方法执行比较久时可以使用代码块优化
 * 当一个线程访问一个对象的synchronized同步代码块时，另一个线程任然可以访问该对象非synchronized同步代码块。执行到同步语句块时 阻塞
 * @author: LinWeiQi
 */
public class Synchronized_this {

    public void t1() throws InterruptedException {
        System.out.println("多个线程进入到这里开始阻塞排队");
        /**
         *  和同步方法一样,这里锁的都是当前对象实例
         */
        synchronized (this) {
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) {
        final Runnable thread01 = new Runnable() {
            final Synchronized_this synchronized_this = new Synchronized_this();
            @Override
            public void run() {
                try {
                    synchronized_this.t1();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(thread01).start();
        new Thread(thread01).start();
        new Thread(thread01).start();
    }

}
