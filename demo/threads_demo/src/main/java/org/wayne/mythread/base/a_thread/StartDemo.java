package org.wayne.mythread.base.a_thread;


/**
 * @Description:
 * stop()结束[可能死锁,不推荐使用]
 * 推荐实现Runnable接口方式开发多线程，因为Java单继承但是可以实现多个接口
 * new Thread()创建线程 start()开启线程, start()方法不保证立即运行,由cpu安排时间片调用,调用run()方法时为普通调用不启动线程
 * synchronized 可解决  **单对象实例变量**   的线程安全问题
 *
 * @author: LinWeiQi
 */
public class StartDemo implements Runnable {
    int count = 5;

    @Override
    synchronized public void run() {
        while (count > 0) {
            count--;
            System.out.println(" 计算，count=" + count);
        }
    }

    public static void main(String[] args) {
        StartDemo t =new StartDemo();
        Thread thread = new Thread(t);
        Thread thread1 = new Thread(t);
        thread.start();
        thread1.start();
    }
  /* 结果
   计算，count=3
    计算，count=2
    计算，count=3
    计算，count=1
    计算，count=0*/

}
