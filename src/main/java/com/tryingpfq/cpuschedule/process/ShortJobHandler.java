package com.tryingpfq.cpuschedule.process;

import com.tryingpfq.cpuschedule.Job;

/**
 * @author tryingpfq
 * @date 2020/11/16
 **/
public class ShortJobHandler extends AbstractProcessHandler{
    //短作业
    @Override
    public void execute(Job job) {

        System.err.println(String.format("short job,jobId[%s]",job.getJobId()));

        //如果这里没有执行完成的任务，需要放到普通队列中去
        //问题是这个时间怎么记录
    }
}
