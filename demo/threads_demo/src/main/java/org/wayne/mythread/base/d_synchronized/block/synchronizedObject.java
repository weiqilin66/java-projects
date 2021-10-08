package org.wayne.mythread.base.d_synchronized.block;

/**
 * @author: LinWeiQi
 */
public class synchronizedObject {
    /**
     * 线程使用了同一个“对象监视器”,所以运行结果是同步的
     * obj最好不要String 因为字符串存在常量池 两个相同的字符串指向同一个地址 会获得相同的锁 导致只有一个线程可以运行
     */
    public void t2(MyObject object){

        //MyObject上锁 锁的是传入对象的地址(对象地址不变就是同一个监视器,=>同一把锁)
        synchronized (object){
            try {
                System.out.println("testMethod1 ____getLock time="
                        + System.currentTimeMillis() + " run ThreadName="
                        + Thread.currentThread().getName());
                Thread.sleep(2000);
                System.out.println("testMethod1 releaseLock time="
                        + System.currentTimeMillis() + " run ThreadName="
                        + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("同对象监视器");
        synchronizedObject s1 = new synchronizedObject();
        MyObject object = new MyObject();
        MyThread t1 = new MyThread(s1,object);
        MyThread t2 = new MyThread(s1,object);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("(测试join)当前运行线程为"+Thread.currentThread().getName());
        System.out.println("不同对象监视器");//不同监视对象不阻塞了
        MyObject object2 = new MyObject();
        MyThread t13 = new MyThread(s1,object);
        MyThread t23 = new MyThread(s1,object2);
        t13.start();
        t23.start();
    }
}
