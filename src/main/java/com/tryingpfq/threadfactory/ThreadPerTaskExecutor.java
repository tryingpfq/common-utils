package com.tryingpfq.threadfactory;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

/**
 *  这里主要可以用于对线程工厂创建的线程做个启动
 *  并不能理解为一个线程池
 *  {@link io.netty.util.concurrent.ThreadPerTaskExecutor}
 * @author tryingpfq
 * @date 2020/5/14
 **/
public final class ThreadPerTaskExecutor implements Executor {

    private final ThreadFactory threadFactory;

    public ThreadPerTaskExecutor(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
    }

    @Override
    public void execute(Runnable command) {
        threadFactory.newThread(command).start();
    }
}
