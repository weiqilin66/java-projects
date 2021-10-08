package org.wayne.mythread.i_daemon;

/**
 * @Description: Thread.sleep() 当前线程休息ms
 * 在main中表示main线程休息ms
 * @author: LinWeiQi
 */
public class MyThread extends Thread {
    int i = 0;
    @Override
    public void run() {
        try {

            while (true){
                i++;
                System.out.println(i);
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
