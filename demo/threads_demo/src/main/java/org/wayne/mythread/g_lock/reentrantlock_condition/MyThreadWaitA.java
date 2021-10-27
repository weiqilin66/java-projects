package org.wayne.mythread.g_lock.reentrantlock_condition;

/**
 * @Description:
 * @author: LinWeiQi
 */
public class MyThreadWaitA extends Thread {
    private TestConditions testConditions;

    public MyThreadWaitA(TestConditions testConditions){
        this.testConditions = testConditions;
    };

    @Override
    public void run() {
        testConditions.awaitA();
    }
}
