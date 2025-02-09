package com.pipe.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ZKClient {

    private String ConnectString = "hadoop100:2181,hadoop102:2181,hadoop103:2181";
    private int sessionTimeout = 2000;
    private ZooKeeper zkClient;

    @Before
    public void test1() throws Exception {
        zkClient = new ZooKeeper(ConnectString, sessionTimeout, new Watcher() {
            public void process(WatchedEvent event) {
//                System.out.println("--------------------------------");
//                List<String> children = null;
//                try {
//                    children = zkClient.getChildren("/", true);  //启动监听
//                    for (String child : children) {
//                        System.out.println(child);
//                    }
//                } catch (KeeperException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        });
    }

    // 修改windows host映射
    @Test
    public void testCreate() throws Exception {
        String nodeCreate = zkClient.create("/pipe", "test.txt".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    @Test
    public void getChildren() throws Exception {
        Thread.sleep(Long.MAX_VALUE);
    }

    @Test
    public void exists() throws Exception {
        Stat stat = zkClient.exists("/pipe", false);  //关闭监听
        System.out.println(stat!= null);
    }




    @Test
    public void testDelete() throws Exception {
        zkClient.delete("/pipe", -1);
    }

    @Test
    public void testUpdate() throws Exception {
        zkClient.setData("/pipe", "test.txt update".getBytes(), -1);
    }

    @Test
    public void getData() throws Exception {
        byte[] data = zkClient.getData("/pipe", false, null);
        System.out.println(new String(data));
    }



}
