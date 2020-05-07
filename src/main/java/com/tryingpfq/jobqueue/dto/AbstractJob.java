package com.tryingpfq.jobqueue.dto;

import com.tryingpfq.jobqueue.enums.JobType;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 *  自定义的一个任务
 * @author tryingpfq
 * @date 2020/5/7
 **/
@Getter
@Setter
public abstract class AbstractJob implements IJob<AbstractJob> {
    //默认延迟三十分钟
    private final static long DELAY = 30 * 60 * 1000L;

    private int jobId;

    private long startTime;

    private long expire;


    public long expire() {
        return expire;
    }

    public AbstractJob(){
        this.startTime = System.currentTimeMillis();
        this.expire = startTime + getDelayTime();
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.expire - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
    }

    @Override
    public JobType jobType() {
        return JobType.None;
    }

    public boolean isExpire() {
        return getDelay(TimeUnit.MILLISECONDS) <= 0;
    }

    public long getDelayTime(){
        return DELAY;
    }

    @Override
    public String toString() {
        return "AbstractJob{" +
                "jobId=" + jobId +
                ", startTime=" + startTime +
                ", expire=" + expire +
                '}';
    }
}
