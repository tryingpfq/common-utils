package com.tryingpfq.cpuschedule.process;

import com.tryingpfq.cpuschedule.Job;

/**
 * @author tryingpfq
 * @date 2020/11/16
 **/
public class NormalHandler extends AbstractProcessHandler{
    @Override
    public void execute(Job job) {
        System.err.printf("normal job execute,jobId [%s]%n", job.getJobId());
    }
}
