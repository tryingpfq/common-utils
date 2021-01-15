package com.tryingpfq.thread.wait;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author tryingpfq
 * @date 2021/1/15
 **/
public class BlockQueue {

    private Queue<String> buffer = new LinkedList<>();

    public String take() throws InterruptedException {
        while (buffer.isEmpty()) {
            //这个demo 这里可能不会被唤醒
            wait();
        }
        return buffer.remove();
    }

    public void give(String data) {
        buffer.add(data);
        notify();
    }
}
