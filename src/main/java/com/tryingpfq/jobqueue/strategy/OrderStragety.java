package com.tryingpfq.jobqueue.strategy;

import com.tryingpfq.jobqueue.dto.AbstractJob;
import com.tryingpfq.jobqueue.enums.JobType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author tryingpfq
 * @date 2020/5/7
 **/
@Component
public class OrderStragety implements IJobStragegy{

    @Autowired
    private JobStragetyHolder stragetyHolder;

    @PostConstruct
    public void init() {
        stragetyHolder.regist(this);
    }


    @Override
    public void doStragety(AbstractJob job) {
        System.err.println("curTime " + System.currentTimeMillis() + "_" + job.toString());
    }

    @Override
    public JobType jobType() {
        return JobType.Order;
    }
}
