package com.tryingpfq.cpuschedule.process;

import com.tryingpfq.cpuschedule.Job;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author tryingpfq
 * @date 2020/11/16
 **/
public abstract class AbstractProcessHandler {

    abstract void execute(Job job);

    public void doExecute(Queue<Job> jobs) {
        while (jobs.poll() != null) {
            Job job = jobs.peek();
            if (job.isInvalid()) {
                continue;
            }
            execute(job);
            job.setCreateTime(System.currentTimeMillis());
        }
    }

    public Queue queue() {
        return new LinkedList();
    }
}
