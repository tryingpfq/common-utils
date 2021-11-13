package com.tryingpfq.threadfactory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author tryingpfq
 * @date 2020/5/14
 **/
public class NameThreadFactory implements ThreadFactory {
    private static final AtomicInteger threadId = new AtomicInteger();
    /**
     * 线程所在的分组
     */
    private final ThreadGroup group;

    private final String prefix;

    private final boolean daemon;

    public NameThreadFactory(ThreadGroup group, String prefix, boolean daemon) {
        this.group = group != null ? group : System.getSecurityManager().getThreadGroup();
        this.prefix = prefix;
        this.daemon = daemon;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r, prefix + threadId.incrementAndGet());
        try {
            if (t.isDaemon() != daemon) {
                t.setDaemon(daemon);
            }
        } catch (Exception ignored) {
            // Doesn't matter even if failed to set.
        }
        return t;
    }
}
