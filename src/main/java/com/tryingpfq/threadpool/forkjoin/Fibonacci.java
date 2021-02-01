package com.tryingpfq.threadpool.forkjoin;

import java.util.concurrent.RecursiveTask;

/**
 * @author tryingpfq
 * @date 2021/1/19
 **/
public class Fibonacci extends RecursiveTask<Integer> {
    private int n;

    public Fibonacci(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if (n <= 1) {
            return n;
        }
        Fibonacci f1 = new Fibonacci(n - 1);
        f1.fork();
        Fibonacci f2 = new Fibonacci(n - 2);
        f2.fork();
        return f1.join() + f2.join();
    }
}
