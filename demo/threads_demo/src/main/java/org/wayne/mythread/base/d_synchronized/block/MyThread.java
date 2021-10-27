package org.wayne.mythread.base.d_synchronized.block;

/**
 * @Description: 测试同步代码块
 * @author: LinWeiQi
 */
public class MyThread extends Thread {
    private MyObject object;
    private SynchronizedObject synchronizedObject;

    public MyThread(SynchronizedObject synchronizedObject, MyObject object){
        this.object = object;
        this.synchronizedObject = synchronizedObject;
    }
    @Override
    public void run() {
        synchronizedObject.t2(object);
    }
}
