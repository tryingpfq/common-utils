package com.tryingpfq.interview.thread;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.TimeUnit;

/**
 * 可见性，里面的一条线程是不会停止的
 * @Author tryingpfq
 * @Date 2021/1/17
 */
@Getter
@Setter
public class Demo {

    private /*volatile*/ boolean running = true;
    public void test() {
        System.err.println("start run ");
        while (running) {
            System.err.println("running");;
        }
        System.err.println("stop run");
    }


    public void stop() {
        System.err.println("try stop");
        this.running = false;
    }


    public static void main(String[] args) {
        Demo demo = new Demo();
        new Thread(demo::test, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        demo.stop();
    }
}
