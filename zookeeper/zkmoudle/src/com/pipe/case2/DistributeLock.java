package com.pipe.case2;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class DistributeLock {

    private final String ConnectString = "hadoop100:2181,hadoop102:2181,hadoop103:2181";;
    private final int sessionTimeout = 2000;
    private final ZooKeeper zk;

    private CountDownLatch latch = new CountDownLatch(1);
    private CountDownLatch waitlatch = new CountDownLatch(1);

    private String waitPath;
    private String createNode;
    private String createNode1;

    public static void main(String[] args) throws InterruptedException, KeeperException, IOException {



    }

    public void zkunlock() throws InterruptedException, KeeperException {
        zk.delete(createNode1, -1);
    }

    public void zklock() throws InterruptedException, KeeperException {
        createNode1 = zk.create("/locks/" + "seq-", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        List<String> children = zk.getChildren("/locks", false);
        if (children.size() == 1){
            return;
        }else{
            Collections.sort(children);
            String thisNode = createNode1.substring("/locks/".length());
            int index = children.indexOf(thisNode);
            if (index == 0){
                return;
            }else if(index == -1){
                System.out.println("error happend");
            }else{
                //监听前一个节点
                waitPath = "/locks/" +  children.get(index-1);
                zk.getData(waitPath, true, null);

                waitlatch.await();

                return;
            }
        }


    }





    public DistributeLock() throws IOException, InterruptedException, KeeperException {
        zk = new ZooKeeper(ConnectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent event) {

                // latch 连接上zk 可以释放
                if (event.getState() == Event.KeeperState.SyncConnected){
                    latch.countDown();
                }

                // waitLatch 需要释放
                if (event.getType() == Event.EventType.NodeDeleted && event.getPath().equals(waitPath)){
                    waitlatch.countDown();
                }


            }
        });

        // zk正常连接，程序继续
        latch.await();

        Stat stat = zk.exists("/locks", false);
        if (stat == null){
            String create = zk.create("/locks", "locks".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
    }
}
