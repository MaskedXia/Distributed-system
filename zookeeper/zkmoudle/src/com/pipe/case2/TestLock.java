package com.pipe.case2;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;

public class TestLock {

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        final DistributeLock lock1 = new DistributeLock();
        final DistributeLock lock2 = new DistributeLock();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock1.zklock();
                    System.out.println("Thread 1 acquired lock");
                    Thread.sleep(5 * 1000);
                    lock1.zkunlock();
                    System.out.println("Thread 1 released lock");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock2.zklock();
                    System.out.println("Thread 2 acquired lock");
                    Thread.sleep(5 * 1000);
                    lock2.zkunlock();
                    System.out.println("Thread 2 released lock");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
