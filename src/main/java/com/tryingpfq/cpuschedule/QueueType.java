package com.tryingpfq.cpuschedule;

import com.tryingpfq.cpuschedule.process.AbstractProcessHandler;
import com.tryingpfq.cpuschedule.process.ExigenceHandler;
import com.tryingpfq.cpuschedule.process.NormalHandler;
import com.tryingpfq.cpuschedule.process.ShortJobHandler;

import java.util.Comparator;
import java.util.Queue;
import java.util.function.Supplier;

/**
 * @author tryingpfq
 * @date 2020/11/16
 **/
public enum QueueType implements Comparator<QueueType> {
    NORMAL(NormalHandler::new),

    SHORT_JOB(ShortJobHandler::new),

    EXIGENCE(ExigenceHandler::new);

    private AbstractProcessHandler handler;

    private Queue<Job> queue;

    QueueType(Supplier<? extends AbstractProcessHandler> supplier) {
        this.handler =  supplier.get();
        this.queue = handler.queue();
    }

    public void execute() {
        handler.doExecute(queue);
    }

    @Override
    public int compare(QueueType o1, QueueType o2) {
        return o1.ordinal() - o2.ordinal();
    }
}
