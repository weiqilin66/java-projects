package org.wayne.mythread.h_executors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Description:
 *  FixedThreadPool 示例
 * @author: LinWeiQi
 */
public class FistExecutor {
    int taskSize = 3;
    //创建一个线程池
    ExecutorService pool = Executors.newFixedThreadPool(taskSize);

    public List<Future> t1() {
        boolean flag =true;
        //创建多个有返回值的任务
        List<Future> list = new ArrayList<Future>();
        for (int i = 0; i < taskSize; i++) {
            Callable c = new Callable() {//Callable有返回值 Runnable无返回值
                @Override
                public Object call() throws Exception {
                    if(flag){
                        return "2";
                    }else {
                        return 1;
                    }
                }
            };
            //执行任务并获取Future对象
            Future f = pool.submit(c);//submit有返回值,execute无返回值
            list.add(f);
        }
        //关闭线程池
        pool.shutdown();
        return list;
    }
    ExecutorService pool2 = Executors.newFixedThreadPool(taskSize);
    public void t2(){
        // for（；；）相当于while（1） 死循环
        for(;;){
            pool2.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName()+"is running");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FistExecutor fistExecutor = new FistExecutor();
        List<Future> list = fistExecutor.t1();
        //获取并发运行结果
        for (Future future : list) {

            System.out.println(future.get().toString());
        }
        fistExecutor.t2();
    }

}
