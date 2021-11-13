package com.tryingpfq.jmx;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 *  JMX 可以在Jconsole控制台看到对应方法和属性
 *
 * @author tryingpfq
 * @date 2020/4/15
 **/
public class QueueMonitor implements QueueMonitorMBean{

    private Queue<Integer> queues = new ArrayDeque<>();


    public static void main(String[] args) throws InterruptedException {
        try {
            QueueMonitor queueMonitor = new QueueMonitor();
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName objectName = new ObjectName("com.tryingpfq.common:type=QueueMonitor");
            mbs.registerMBean(queueMonitor,objectName);
            int i = 0;
            while (true) {
                Thread.sleep(5000L);
                queueMonitor.add(i++);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread.sleep(Integer.MAX_VALUE);
        //Jconsole 控制台命令

    }

    @Override
    public int getQueue_Size() {
        return queues.size();
    }

    public void add(Integer integer) {
        queues.add(integer);
    }

}
