package com.xytyzgf.concurrency.ReentrantLcok;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2017/3/13.
 */
public class ReentrantLockConditon implements Runnable {
    static ReentrantLock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new ReentrantLockConditon());
        t1.start();
        Thread.sleep(1000);
        lock.lock();
        System.out.println("mian thread signal other thread.");
        condition.signal();
        lock.unlock();

    }

    public void run() {
        try {
            lock.lock();
            System.out.println("thread await");
            //类似于wait(),释放重入锁并等待
            condition.await();
            System.out.println("thread is going on.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
