package com.tryingpfq.jobqueue.dto;

import com.tryingpfq.jobqueue.enums.JobType;

import java.util.concurrent.Delayed;

/**
 * @author tryingpfq
 * @date 2020/5/7
 **/
public interface IJob<T extends IJob> extends Delayed {

    int getJobId();

    long expire();

    JobType jobType();
}
