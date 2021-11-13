package com.tryingpfq.jobqueue.strategy;

import com.tryingpfq.jobqueue.enums.JobType;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tryingpfq
 * @date 2020/5/7
 **/
@Component
public class JobStragetyHolder {
    public ConcurrentHashMap<JobType, IJobStragegy> jobStragegys = new ConcurrentHashMap<>(JobType.values().length);

    public void regist(IJobStragegy stragegy) {
        jobStragegys.putIfAbsent(stragegy.jobType(), stragegy);
    }

    public IJobStragegy getJobStragegy(JobType type) {
        return jobStragegys.get(type);
    }
}
