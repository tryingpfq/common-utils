package com.tryingpfq.zookeeper.javaapi;

import com.tryingpfq.zookeeper.ZkCons;
import lombok.SneakyThrows;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @Author tryingpfq
 * @Date 2020/11/7
 */
public class ZookeeperDemo implements Watcher {

    private static ZooKeeper zooKeeper;


    @SneakyThrows
    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            if (watchedEvent.getType() == Event.EventType.NodeCreated) {
                System.err.println("create Node " + watchedEvent.getPath());
                zooKeeper.getData(watchedEvent.getPath(), true, new Stat());
            }
            if (watchedEvent.getType() == Event.EventType.NodeDataChanged) {
                System.err.println("node change " + watchedEvent.getPath());

            }
        }
    }

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        zooKeeper = new ZooKeeper(ZkCons.CONNECT_STR, 2000, new ZookeeperDemo());

        zooKeeper.create("/demo", "demo".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zooKeeper.getData("/demo", true, new Stat());
        TimeUnit.SECONDS.sleep(2);   

        zooKeeper.setData("/demo", "demo1".getBytes(), -1);
        TimeUnit.SECONDS.sleep(2);



    }
}
