package com.xytyzgf.concurrency.ReentrantLcok;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁初探
 * Created by Administrator on 2017/3/12.
 */
public class ReentrantLockDemo implements Runnable {
    static ReentrantLock lock = new ReentrantLock();
    static int i = 0;

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockDemo d1 = new ReentrantLockDemo();
        ReentrantLockDemo d2 = new ReentrantLockDemo();
        Thread t1 = new Thread(d1, "t1");
        Thread t2 = new Thread(d1, "t2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }

    public void run() {
        for (int j = 0; j < 1000; j++) {
            lock.lock();
            try {
                i++;
            } finally {
                lock.unlock();
            }
        }
    }
}
