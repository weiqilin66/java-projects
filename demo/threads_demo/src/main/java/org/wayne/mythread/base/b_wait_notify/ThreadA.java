package org.wayne.mythread.base.b_wait_notify;

/**
 * @Description:
 * @author: LinWeiQi
 */
public class ThreadA extends Thread {

    private Object object;

    public ThreadA(Object object) {
        super();
        this.object = object;
    }

    @Override
    public void run() {
        try {
            synchronized (object) {

                if (MyList.size() != 5) {
                    System.out.println("wait begin " + System.currentTimeMillis());
                    Thread.sleep(5000);
                    object.wait();
                    System.out.println("老子醒了");
                    System.out.println("测试MyList.size()是否从主存中读取: "+MyList.size());
                    System.out.println("wait end  " + System.currentTimeMillis());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}