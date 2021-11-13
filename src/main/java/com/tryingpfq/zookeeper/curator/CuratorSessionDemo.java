package com.tryingpfq.zookeeper.curator;

import org.apache.zookeeper.CreateMode;

/**
 * @author tryingpfq
 * @date 2020/11/9
 **/
public class CuratorSessionDemo {

    public static void main(String[] args) {

        //创建节点
        try {
            CuratorUtils.getInstance().create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)
                    .forPath("node1", "123".getBytes());
        } catch (Exception e) {

        }

    }
}
