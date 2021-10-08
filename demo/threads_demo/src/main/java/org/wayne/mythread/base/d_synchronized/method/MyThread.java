package org.wayne.mythread.base.d_synchronized.method;

/**
 * @Description:
 * @author: LinWeiQi
 */
public class MyThread extends Thread {
    private SynchronizedDemo obj;

    public MyThread(SynchronizedDemo obj) {
        super();
        this.obj = obj;
    }
    //设置 对象的属性值
    @Override
    public void run() {
        super.run();
        obj.setValue("B", "BB");
    }
}
