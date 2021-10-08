package org.wayne.mythread._example;

/**
 * @Description: 队列+锁机制
 * @author: lwq
 */
public class SafeWeb12306Synchronized implements Runnable {
    private int num = 20;
    @Override
    synchronized public void run() {
        while (true){
            if (num<0) {
                break;
            }
            // 模拟网络延迟, 出现了负数情况
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"-->余票:" + num--);
        }
    }

    public static void main(String[] args) {
        // 一份资源(单实例)
        // 或者使用多实例但是票资源num为static只有一份实例变量
        final SafeWeb12306Synchronized obj = new SafeWeb12306Synchronized();
        // 多线程强
        new Thread(obj,"线程1").start();
        new Thread(obj,"线程2").start();
        new Thread(obj,"线程3").start();
    }
}
