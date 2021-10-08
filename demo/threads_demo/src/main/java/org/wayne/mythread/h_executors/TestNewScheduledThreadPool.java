package org.wayne.mythread.h_executors;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 创建一个线程池,它可安排在给定延迟后运行命令或者定期执行
 * @author: LinWeiQi
 */
public class TestNewScheduledThreadPool {

    public static void main(String[] args) {

        ScheduledExecutorService pool = Executors.newScheduledThreadPool(3);
        pool.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("延迟3秒");
            }
        },3,TimeUnit.SECONDS);
        pool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("延迟1秒每3秒执行一次");
            }
        },1,3,TimeUnit.SECONDS);

    }
}
