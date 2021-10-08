package org.wayne.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @Description: 定时任务基础类
 * @author: lwq
 */
public class BaseJobQ implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        doExecute();
    }

    // 子类重写 Job
    protected void doExecute() {

    }
    // 子类重写 触发器

}
