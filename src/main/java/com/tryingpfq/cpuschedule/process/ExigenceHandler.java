package com.tryingpfq.cpuschedule.process;

import com.tryingpfq.cpuschedule.Job;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author tryingpfq
 * @date 2020/11/16
 **/
public class ExigenceHandler extends AbstractProcessHandler{

    @Override
    public void execute(Job job) {
        //紧急任务，全部执行
    }

    @Override
    public Queue queue() {
        return new PriorityQueue();
    }
}
