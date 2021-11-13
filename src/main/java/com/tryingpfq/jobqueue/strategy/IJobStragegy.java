package com.tryingpfq.jobqueue.strategy;

import com.tryingpfq.jobqueue.dto.AbstractJob;
import com.tryingpfq.jobqueue.enums.JobType;

/**
 * @author tryingpfq
 * @date 2020/5/7
 **/
public interface IJobStragegy<T extends AbstractJob> {
    void doStragety(T job);

    JobType jobType();
}
