package com.tryingpfq.jobqueue.handler;

import com.tryingpfq.jobqueue.dto.AbstractJob;
import com.tryingpfq.jobqueue.strategy.IJobStragegy;
import com.tryingpfq.jobqueue.strategy.JobStragetyHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author tryingpfq
 * @date 2020/5/7
 **/
@Component
public class JobHandler<T extends AbstractJob> extends AbstractHandler implements Runnable{

    private static final Logger log = LoggerFactory.getLogger(JobHandler.class);

    @Autowired
    private JobStragetyHolder stragetyHolder;

    /**
     * 消费定时任务
     */
    private static ScheduledThreadPoolExecutor executor;

    /**
     * 初始延迟
     */
    private long initialDelay;

    /**
     * 周期
     */
    private int period;



    private AtomicBoolean started = new AtomicBoolean(false);


    static{
        ThreadGroup group = new ThreadGroup("定时任务");
        executor = new ScheduledThreadPoolExecutor(2, new ThreadFactory() {
            final AtomicInteger nums = new AtomicInteger(1);
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(group, r, group.getName() + "_" + nums.incrementAndGet(), 0);
                return t;
            }
        });
    }


    @PostConstruct
    public void init(){
        start();
        initialDelay = 10L;
        period = 10;
        executor.scheduleAtFixedRate(this, initialDelay, period, TimeUnit.SECONDS);
        System.err.println("scheduled is start");
    }

    @Override
    public void run() {
        if (!started.get()) {
            log.error("");
            return;
        }
        /**
         *  这里先以超时机制处理吧 其实队列本身可以进行阻塞的，这个线程池不一定需要。
         *  这里轮询主要是以线程池中周期轮行的。
         */
        AbstractJob take = poll(period,TimeUnit.MILLISECONDS);
        if (take == null) {
            log.info("task is null");
        }else{
            if (!take.isExpire()) {
                return;
            }
            IJobStragegy iJobStragegy = stragetyHolder.getJobStragegy(take.jobType());
            if (iJobStragegy == null) {
                log.error("taskJob type:{},stragegy is null ",take.jobType());
                return;
            }
            cancel(take);
            iJobStragegy.doStragety(take);
        }
    }

    @Override
    boolean start() {
        return started.compareAndSet(false, true);
    }

    @Override
    boolean shutdown() {
        if (started.compareAndSet(true, false)) {
            executor.shutdown();
            return true;
        }
        return false;
    }
}
