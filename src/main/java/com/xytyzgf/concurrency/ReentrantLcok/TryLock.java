package com.xytyzgf.concurrency.ReentrantLcok;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2017/3/12.
 */
public class TryLock implements Runnable {

    static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread t1 = new Thread(new TryLock(), "t1");
        Thread t2 = new Thread(new TryLock(), "t2");
        t1.start();
        t2.start();
    }

    public void run() {
        String threadName = Thread.currentThread().getName();
        try {
            System.out.println(threadName + "尝试抢占重入锁");
            if (lock.tryLock(5, TimeUnit.SECONDS)) {
                System.out.println(threadName + "抢占到锁");
                Thread.sleep(4000);
//                Thread.sleep(6000);
            } else {
                System.out.println(threadName + "抢占失败！");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock.isHeldByCurrentThread())
                lock.unlock();
            System.out.println(threadName + "结束---" + System.currentTimeMillis());
        }
    }
}
