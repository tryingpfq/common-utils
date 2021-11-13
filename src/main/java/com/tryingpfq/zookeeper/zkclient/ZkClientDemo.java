package com.tryingpfq.zookeeper.zkclient;

import com.tryingpfq.zookeeper.ZkCons;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;

/**
 * @Author tryingpfq
 * @Date 2020/11/8
 */
public class ZkClientDemo {

    private static ZkClient zkClient;

    public static void main(String[] args) {
        zkClient = new ZkClient(ZkCons.CONNECT_STR, 5000);

        zkClient.createEphemeral("/zkClient", "zlClient");

        zkClient.subscribeDataChanges("/zkClient", new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                System.err.println();
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {

            }
        });
    }
}
