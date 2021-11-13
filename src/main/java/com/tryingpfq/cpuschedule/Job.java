package com.tryingpfq.cpuschedule;

import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author tryingpfq
 * @date 2020/11/16
 **/
@Getter
@Setter
public class Job implements Comparator<Job> {
    private static final AtomicInteger ID_INCREASE = new AtomicInteger(0);

    private int jobId;

    private QueueType queueType;

    private int priority;

    private long needTime;

    private long createTime;

    private long currentTime;

    private boolean isInvalid;

    public static Job valueOf(QueueType queueType,long needTime) {
        Job job = new Job();
        job.jobId = ID_INCREASE.incrementAndGet();
        job.queueType = queueType;
        job.createTime = System.currentTimeMillis();
        return job;
    }

    @Override
    public int compare(Job o1, Job o2) {
        return o1.getPriority() - o2.getPriority() != 0 ? o1.getPriority() - o2.getPriority() :
                (int) (o1.getNeedTime() - o2.getNeedTime());
    }
}
