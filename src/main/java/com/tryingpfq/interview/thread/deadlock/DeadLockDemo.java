package com.tryingpfq.interview.thread.deadlock;

import java.util.concurrent.TimeUnit;

/**
 * 死锁  jstack xxxx 这个命令可以找到这个死锁信息
 * @author tryingpfq
 * @date 2021/1/18
 **/
public class DeadLockDemo {

    public static void main(String[] args) throws InterruptedException {

        Object obj1 = new Object();

        Object obj2 = new Object();

        new Thread(() ->{
            synchronized (obj1) {
                try {
                    System.err.println("get obj1 lock");
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (obj2) {

                }
            }
        },"t1").start();

        new Thread(() ->{
            synchronized (obj2) {
                try {
                    System.err.println("get obj2 lock");
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (obj1) {

                }
            }
        },"t2").start();

        TimeUnit.SECONDS.sleep(10000);
    }

}
