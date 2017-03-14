package com.xytyzgf.concurrency.LockSupport;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by Administrator on 2017/3/14.
 */
public class LcokSupportInterrupt implements Runnable {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new LcokSupportInterrupt(), "t1");
        Thread t2 = new Thread(new LcokSupportInterrupt(), "t2");
        t1.start();
        t2.start();
        Thread.sleep(100);
        t1.interrupt();
        LockSupport.unpark(t2);

    }

    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.println("thread " + threadName + " is running.");
        System.out.println("thread " + threadName + " packed.");
        LockSupport.park();
        //pack()方法中断的线程不会抛出中断异常，这与它的机制有关
        //Thread.interrupted()获得中断标记，并清空中断标记
        if (Thread.interrupted()) {
            System.out.println("thread " + threadName + " has safe interrupted.");
        }
        System.out.println("thread " + threadName + " has finished.");
        Thread.yield();
    }
}
