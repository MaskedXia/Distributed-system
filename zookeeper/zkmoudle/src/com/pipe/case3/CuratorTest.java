package com.pipe.case3;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class CuratorTest {

    public static void main(String[] args) {

        final InterProcessMutex lock1 = new InterProcessMutex(getCuratorFramework(), "/locks");
        final InterProcessMutex lock2 = new InterProcessMutex(getCuratorFramework(), "/locks");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock1.acquire();
                    System.out.println("Thread 1 acquired the lock");
                    Thread.sleep(5000);
                    lock1.release();
                    System.out.println("Thread 1 released the lock");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock2.acquire();
                    System.out.println("Thread 2 acquired the lock");
                    Thread.sleep(5000);
                    lock2.release();
                    System.out.println("Thread 2 released the lock");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    private static CuratorFramework getCuratorFramework() {
        ExponentialBackoffRetry policy = new ExponentialBackoffRetry(3000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("hadoop100:2181,hadoop102:2181,hadoop103:2181")
                .connectionTimeoutMs(2000)
                .sessionTimeoutMs(2000)
                .retryPolicy(policy)
                .build();
        client.start();
        System.out.println("zookeeper running successfully");

        return client;
    }
}
