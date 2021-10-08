package org.wayne.mythread.g_lock.reentrantlock_condition;

/**
 * @Description:
 * @author: LinWeiQi
 */
public class MyThread2 extends Thread {
    private TestConditions testConditions;

    public MyThread2(TestConditions testConditions){
        this.testConditions = testConditions;
    };

    @Override
    public void run() {
        testConditions.awaitB();
    }
}
