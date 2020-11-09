package com.tryingpfq.zookeeper.curator;

import com.tryingpfq.zookeeper.ZkCons;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author tryingpfq
 * @date 2020/11/9
 **/
public class CuratorUtils {

    private static CuratorFramework curatorFramework;

    static {
        curatorFramework = CuratorFrameworkFactory.builder().connectString(ZkCons.CONNECT_STR)
                .connectionTimeoutMs(5000).sessionTimeoutMs(5000)
                .namespace("/curator").retryPolicy(new ExponentialBackoffRetry(3000, 3))
                .build();
    }

    public static CuratorFramework getInstance() {
        return curatorFramework;
    }
}
