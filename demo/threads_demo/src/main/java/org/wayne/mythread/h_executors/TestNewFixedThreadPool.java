package org.wayne.mythread.h_executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description: 创建一个可重用的固定线程数的线程池,以共享的无界队列方式来运行这些线程
 *  优点: 可控制线程最大并发数,超出的线程会在队列中等待
 * @author: LinWeiQi
 */
public class TestNewFixedThreadPool  {
    //固定线程数量3
    //忙时提交任务,任务会进入队列中等待,如果由于失败导致任何线程终止,会自动创建新线程代替它
    //在某个线程被显式关闭之前,池中的线程将一直存在
    ExecutorService pool = Executors.newFixedThreadPool(3);

    public void t(){
        int n = 0;
        for(;;){
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName()+"is running");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println(String.format("pool.execute执行%s次", n++));//线程池3个都被占用时其他的进入等待,等待的多了会outOfMemory
        }
    }

    public static void main(String[] args) {
        new TestNewFixedThreadPool().t();

    }

}
