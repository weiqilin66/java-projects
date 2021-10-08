package org.wayne.mythread.base.b_wait_notify;

/**
 *
 * notify()执行后并不会立即释放锁,必须执行完notify()方法所在的synchronized代码块后才释放
 *
 */
public class ThreadB extends Thread {
    private Object object;

    public ThreadB(Object object) {
        super();
        this.object = object;
    }

    @Override
    public void run() {
        try {
            synchronized (object) {
                for (int i = 0; i < 10; i++) {
                    MyList.add();
                    System.out.println("添加了" + (i + 1) + "个元素!");
                    if (MyList.size() == 5) {
                        object.notify();
                        System.out.println("已发出通知！");
                        //可通过return释放锁
//                        return;
                    }
                    Thread.sleep(1000);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
