package com.tryingpfq.zookeeper.curator;

import com.tryingpfq.zookeeper.ZkCons;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * @Author tryingpfq
 * @Date 2020/11/8
 */
public class CuratorDemo {

    private static CuratorFramework curatorFramework;

    public static void main(String[] args) throws Exception {
         curatorFramework = CuratorFrameworkFactory.builder().connectString(ZkCons.CONNECT_STR).
                 sessionTimeoutMs(5000).connectionTimeoutMs(5000).namespace("/curator").
                 retryPolicy(new ExponentialBackoffRetry(1000,3)).build();


        curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).
                forPath("/node1","123".getBytes());


    }
}
