package com.tryingpfq.other;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author tryingpfq
 * @date 2020/12/1
 **/
public class Demo{

    public Demo() {
        System.err.println("demo");
    }

    public static void main(String[] args) {
        Demo demo = new Demo();

        ConcurrentLinkedQueue<Integer> ids = new ConcurrentLinkedQueue<>();
        ids.poll();
        System.err.println("");
    }
}
