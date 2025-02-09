package com.pipe.case1;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class DistributeClient {

    private String ConnectString = "hadoop100:2181,hadoop102:2181,hadoop103:2181";;
    private int sessionTimeout = 2000;
    private ZooKeeper zk;

    public static void main(String[] args) throws InterruptedException, KeeperException, IOException {
        DistributeClient client = new DistributeClient();

        client.getConnect();

        client.getServerList();

        client.business();

    }

    private void getServerList() throws InterruptedException, KeeperException {
        ArrayList<String> servers = new ArrayList<String>();

        List<String> children = zk.getChildren("/servers", true);
        for (String child : children) {
            byte[] data = zk.getData("/servers/" + child, false, null); //不监听，不打印状态
            servers.add(new String(data));
        }
        System.out.println(servers);
    }

    private void business() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }



    private void getConnect() throws IOException {
        zk = new ZooKeeper(ConnectString, sessionTimeout, new Watcher() {
            public void process(WatchedEvent event) {
                try {
                    getServerList();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (KeeperException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
