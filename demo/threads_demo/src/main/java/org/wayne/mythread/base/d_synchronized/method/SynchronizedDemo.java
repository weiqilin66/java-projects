package org.wayne.mythread.base.d_synchronized.method;

/**
 * @Description: Synchronized方法锁, 块级锁
 * 都会大大影响性能
 * 块级锁性能好些,但是可能锁不住,看锁的范围,过大等于方法锁
 * @author: LinWeiQi
 */
public class SynchronizedDemo {
    public String username = "A";
    public String password = "AA";

    // 方法锁
    // 锁数据修改的那个方法
    synchronized public void setValue(String username, String password) {
        try {
            this.username = username;
            System.out.println("线程"+Thread.currentThread().getName()+" 修改了username= BB,password =BB 完成");
            Thread.sleep(5000);
            this.password = password;

            System.out.println("setValue method thread name="
                    + Thread.currentThread().getName() + " username="
                    + username + " password=" + password);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //该方法前加上synchronized关键字就同步了
    public void getValue() {
        System.out.println("getValue method thread name="
                + Thread.currentThread().getName() + " username=" + username
                + " password=" + password);
    }

    synchronized public void getSynValue() {
        System.out.println("getValue method thread name="
                + Thread.currentThread().getName() + " username=" + username
                + " password=" + password);
    }
}
