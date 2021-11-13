package com.tryingpfq.cpuschedule;

import java.io.IOException;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author tryingpfq
 * @date 2020/11/16
 **/
public class ProcessExecute {
    /**
     * 任务队列类型，根据任务优先级排序(暂时是默认的)
     */
    private static final PriorityQueue<QueueType> queueLevel = new PriorityQueue<>(3);

    private static volatile AtomicBoolean start;

    static{
        for (QueueType type : QueueType.values()) {
            queueLevel.add(type);
        }
    }

    public static void main(String[] args) {
        start.set(true);

        new Thread(() ->{
            while (start.get()) {
                for (QueueType queueType : queueLevel) {

                }
            }
        }).start();

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
