package org.wayne.mythread.h_executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description: 创建一个根据需要创建新线程的线程池,在以前创建的线程可用时依然会重用他们
 * @author: LinWeiQi
 */
public class TestNewCachedThreadPool {
    //execute将重用以前构造的线程(如果可用),如果没有可用则创建一个添加到池中,
    //终止并从缓存中移除那些已有60秒未被使用的线程

    ExecutorService pool = Executors.newCachedThreadPool();
}
