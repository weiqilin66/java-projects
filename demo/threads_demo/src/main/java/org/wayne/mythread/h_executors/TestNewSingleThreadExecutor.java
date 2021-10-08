package org.wayne.mythread.h_executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description: 只有一个线程,死后(或发生异常)重新启动一个线程替代原来的线程继续执行下去
 * @author: LinWeiQi
 */
public class TestNewSingleThreadExecutor {
    ExecutorService pool = Executors.newSingleThreadExecutor();
}
