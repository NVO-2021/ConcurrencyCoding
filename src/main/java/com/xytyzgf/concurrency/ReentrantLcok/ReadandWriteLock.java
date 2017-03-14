package com.xytyzgf.concurrency.ReentrantLcok;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Administrator on 2017/3/13.
 */
public class ReadandWriteLock {
    static Lock lock = new ReentrantLock();
    static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    static Lock readLock = readWriteLock.readLock();
    static Lock writeLoke = readWriteLock.writeLock();
    static int value;

    public static void main(String[] args) {
        final ReadandWriteLock rw = new ReadandWriteLock();
        Runnable readThead = new Runnable() {
            public void run() {
                try {
                    System.out.println("R" + Thread.currentThread().getId() + "- value: " + rw.handleRead(readLock));
//                    rw.handleRead(lock);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        };
        final Runnable writeThead = new Runnable() {
            public void run() {
                try {
                    int random = new Random().nextInt(100);
                    rw.handleWrite(writeLoke, random);
//                    rw.handleWrite(lock,new Random().nextInt(100));
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        };

        for (int i = 0; i < 18; i++) {
            new Thread(readThead).start();
        }
        for (int i = 18; i < 20; i++) {
            new Thread(writeThead).start();
        }


    }

    /**
     * 模拟读操作
     *
     * @param lock
     * @return
     */
    public int handleRead(Lock lock) throws InterruptedException {
        try {
            lock.lock();
            Thread.sleep(1000);
            return value;
        } finally {
            lock.unlock();
        }
    }

    public void handleWrite(Lock lock, int num) throws InterruptedException {
        try {
            lock.lock();
            Thread.sleep(500);
            value = num;
            System.out.println("W" + Thread.currentThread().getId() + "- value: " + value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
