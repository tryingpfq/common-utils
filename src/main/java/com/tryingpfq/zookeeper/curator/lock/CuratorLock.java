package com.tryingpfq.zookeeper.curator.lock;

import com.tryingpfq.zookeeper.curator.CuratorUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author tryingpfq
 * @date 2020/11/9
 **/
public class CuratorLock {

    private static final String CURATOR_LOCK = "/curatorLock";

    public static void doLock(CuratorFramework curatorFramework) {
        System.err.println(Thread.currentThread().getName() + "start try acquire lock");

        //zk分布式锁
        InterProcessMutex mutex = new InterProcessMutex(curatorFramework, CURATOR_LOCK);

        try {
            if (mutex.acquire(5, TimeUnit.SECONDS)) {
                System.err.println(Thread.currentThread().getName() + " acquire Lock Success");
                TimeUnit.SECONDS.sleep(1000);

                //do
            }
        } catch (Exception e) {

        }finally {
            try {
                mutex.release();
            } catch (Exception e) {

            }
        }
    }

    public static void main(String[] args) {
        // 定义线程池
        ExecutorService service = Executors.newCachedThreadPool();
        // 定义信号灯，只能允许10个线程并发操作
        final Semaphore semaphore = new Semaphore(10);
        // 模拟10个客户端
        for(int i=0; i < 10 ;i++){
            Runnable runnable = () -> {
                try {
                    semaphore.acquire();
                    // 连接 ZooKeeper  这里要注意哦 要是一个不同的连接
                    CuratorFramework framework = CuratorUtils.getInstance();
                    // 启动
                    framework.start();
                    doLock(framework);
                    semaphore.release();
                } catch (Exception e) {

                }
            };
            service.execute(runnable);
        }
        service.shutdown();
    }

}
