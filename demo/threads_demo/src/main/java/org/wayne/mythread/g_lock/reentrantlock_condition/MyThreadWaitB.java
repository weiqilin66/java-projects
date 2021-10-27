package org.wayne.mythread.g_lock.reentrantlock_condition;

/**
 * @Description:
 * @author: LinWeiQi
 */
public class MyThreadWaitB extends Thread {
    private TestConditions testConditions;

    public MyThreadWaitB(TestConditions testConditions){
        this.testConditions = testConditions;
    };

    @Override
    public void run() {
        testConditions.awaitB();
    }
}
