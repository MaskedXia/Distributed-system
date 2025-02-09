package com.pipe.case1;

import org.apache.zookeeper.*;
import org.apache.zookeeper.client.ConnectStringParser;

import java.io.IOException;

public class DistributeServer {

    private String ConnectString = "hadoop100:2181,hadoop102:2181,hadoop103:2181";;
    private int sessionTimeout = 2000;
    private ZooKeeper zk;

    public static void main(String[] args) throws InterruptedException, KeeperException, IOException {
        DistributeServer server = new DistributeServer();

        server.getConnect();

        server.regist(args[0]);
        
        server.business();

    }

    private void business() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }

    private void regist(String host) throws InterruptedException, KeeperException {
        String create = zk.create("/servers/" + host, host.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(host + " is registered");

    }

    private void getConnect() throws IOException {
        zk = new ZooKeeper(ConnectString, sessionTimeout, new Watcher() {
            public void process(WatchedEvent event) {

            }
        });
    }
}
