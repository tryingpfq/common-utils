package com.tryingpfq.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.TimeUnit;

/**
 * @author tryingpfq
 * @date 2020/11/9
 **/
public class CuratorEventDemo {


    public static void nodeCache() throws Exception{
        final String path = "/nodeCache";

        final CuratorFramework client = CuratorUtils.getInstance();
        client.start();

        try {
            client.delete().deletingChildrenIfNeeded().forPath(path);

            client.create().withMode(CreateMode.EPHEMERAL).forPath("/test", "text".getBytes());
        } catch (Exception e) {

        }
        final NodeCache nodeCache = new NodeCache(client, path);

        nodeCache.start(true);
        nodeCache.getListenable().addListener(() ->{
            System.err.println("nodeChange");
        });

        client.setData().forPath(path, "newData".getBytes());

        TimeUnit.SECONDS.sleep(3);
    }

    public static void pathChildCache() throws Exception{
        final String path = "/pathChildrenCache";
        final CuratorFramework client = CuratorUtils.getInstance();
        client.start();

        final PathChildrenCache cache = new PathChildrenCache(client, path, true);
        cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        cache.getListenable().addListener((client1, event) -> {
            switch (event.getType()) {
                case CHILD_ADDED:
                    System.out.println("CHILD_ADDED:" + event.getData().getPath());
                    break;
                case CHILD_REMOVED:
                    System.out.println("CHILD_REMOVED:" + event.getData().getPath());
                    break;
                case CHILD_UPDATED:
                    System.out.println("CHILD_UPDATED:" + event.getData().getPath());
                    break;
                case CONNECTION_LOST:
                    System.out.println("CONNECTION_LOST:" + event.getData().getPath());
                    break;
                case CONNECTION_RECONNECTED:
                    System.out.println("CONNECTION_RECONNECTED:" + event.getData().getPath());
                    break;
                case CONNECTION_SUSPENDED:
                    System.out.println("CONNECTION_SUSPENDED:" + event.getData().getPath());
                    break;
                case INITIALIZED:
                    System.out.println("INITIALIZED:" + event.getData().getPath());
                    break;
                default:
                    break;
            }
        });

        Thread.sleep(1000);

        client.create().withMode(CreateMode.PERSISTENT).forPath(path + "/c1");
        Thread.sleep(1000);

        client.delete().forPath(path + "/c1");
        Thread.sleep(1000);
    }
}
