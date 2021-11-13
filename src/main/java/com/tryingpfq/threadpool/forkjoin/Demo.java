package com.tryingpfq.threadpool.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * @author tryingpfq
 * @date 2021/1/19
 **/
public class Demo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        for (int i = 0; i < 10; i++) {
            ForkJoinTask<Integer> task = forkJoinPool.submit(new Fibonacci(i));
            System.err.println(task.get());
        }
    }
}
