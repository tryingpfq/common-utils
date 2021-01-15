package com.tryingpfq.thread;

/**
 * @author tryingpfq
 * @date 2021/1/14
 **/
public class ThreadStopDemo {

    private static boolean isCancel = false;

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            while (!isCancel) {
                System.err.println("run thread1");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        thread1.start();
        Thread.sleep(3000);
        Thread thread2 = new Thread(() -> {
            isCancel = true;
            System.err.println("iscacle");
        });

        thread2.start();
        Thread.sleep(30000);
    }
}
