package com.xytyzgf.concurrency.ReentrantLcok;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock允许中断阻塞的线程，解决死锁问题
 * Created by Administrator on 2017/3/12.
 */
public class InLock implements Runnable {


    static ReentrantLock lock1 = new ReentrantLock();
    static ReentrantLock lock2 = new ReentrantLock();
    ThreadLocal<String> threadName;

    int lock;

    public InLock(int num) {
        this.lock = num;
    }

    public static void main(String[] args) {
        try {
            InLock i1 = new InLock(2);
            InLock i2 = new InLock(1);
            Thread t1 = new Thread(i1, "T1");
            Thread t2 = new Thread(i2, "T2");
            t1.start();
            t2.start();
            Thread.sleep(2000);
            t2.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "执行结束");
        }
    }

    public void run() {
        if (threadName == null) {
            threadName = new ThreadLocal<String>();
            threadName.set(Thread.currentThread().getName());
        }
        try {
            if (lock == 1) {
                lock1.lockInterruptibly();
                Thread.sleep(1000);
                System.out.println("线程[" + threadName.get() + "]休眠1s");
                lock2.lockInterruptibly();
            } else {
                lock2.lockInterruptibly();
                Thread.sleep(1000);
                System.out.println("线程[" + threadName.get() + "]休眠1s");
                lock1.lockInterruptibly();

            }
        } catch (InterruptedException e) {
            System.out.println("线程[" + threadName.get() + "]出现问题");
            e.printStackTrace();
        } finally {
            if (lock1.isHeldByCurrentThread())
                lock1.unlock();
            if (lock2.isHeldByCurrentThread())
                lock2.unlock();
            System.out.println("线程[" + threadName.get() + "]执行完毕，退出");
        }
    }
}
