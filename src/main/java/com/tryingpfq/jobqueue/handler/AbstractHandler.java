package com.tryingpfq.jobqueue.handler;

import com.tryingpfq.jobqueue.dto.AbstractJob;
import com.tryingpfq.jobqueue.dto.IJob;

import java.util.Iterator;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author tryingpfq
 * @date 2020/5/7
 **/
public abstract class AbstractHandler<T extends AbstractJob>{

    private final DelayQueue<T> DELAY_QUEUE = new DelayQueue<>();

    private void initailizeJob(){

    }


    abstract boolean start();

    abstract boolean shutdown();

    public boolean addJob(T job){
        return DELAY_QUEUE.add(job);
    }

    T take() {
        try {
            return DELAY_QUEUE.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    T poll() {
        return DELAY_QUEUE.poll();
    }

    T poll(long timeOut, TimeUnit unit) {
        try {
            return DELAY_QUEUE.poll(timeOut, unit);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    boolean cancel(T job) {
        if (job == null) {
            return false;
        }
        /**
         * 这个地方要优化
         */
        Iterator<T> iterator = DELAY_QUEUE.iterator();
        while (iterator.hasNext()) {
            T cur = iterator.next();
            if (cur.getJobId() == job.getJobId()) {
                DELAY_QUEUE.remove(cur);
                return true;
            }
        }
        return false;
    }
}
