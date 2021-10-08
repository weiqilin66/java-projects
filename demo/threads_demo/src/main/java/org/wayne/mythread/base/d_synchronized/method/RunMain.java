package org.wayne.mythread.base.d_synchronized.method;

/**
 * @Description:
 * synchronized取得的锁都是对象锁，而不是把一段代码或方法当做锁。
 * 哪个线程先执行带synchronized关键字的方法，则哪个线程就持有该方法所属对象的锁Lock，
 * 那么其他线程只能呈等待状态，前提是多个线程访问的是同一个对象

 * @author: LinWeiQi
 */
public class RunMain {
        public static void main(String[] args) {
            try {
                SynchronizedDemo obj = new SynchronizedDemo();
                System.out.println("初始值:");
                obj.getValue();
                MyThread thread = new MyThread(obj);
                thread.start();
                //打印结果受此值大小影响
                Thread.sleep(200);

                System.out.println("非同步getValue:(立即获得)");
                obj.getValue();
                System.out.println("");
                System.out.println("同步getSynValue:(由于thread获得了对象obj锁所以执行此方法会等待5s)");
                obj.getSynValue();
                Thread.sleep(5000);
                System.out.println("非同步getValue:");
                obj.getValue();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
/**
 *  *
 *  * “可重入锁”
 *  * 自己可以再次获取自己的内部锁。比如一个线程获得了某个对象的锁，此时这个对象锁还没有释放，当其再次想要获取这个对象的锁的时候还是可以获取的
 *  * ，如果不可锁重入的话，就会造成死锁
 *  * 当存在父子类继承关系时，子类是完全可以通过“可重入锁”调用父类的同步方法
 *  * 同步不具有继承性
 */
}
