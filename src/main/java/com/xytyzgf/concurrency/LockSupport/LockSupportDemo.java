package com.xytyzgf.concurrency.LockSupport;

import java.util.concurrent.locks.LockSupport;

/**
 * 线程阻塞工具类:
 * LockSupport.pack()采用类似信号量的机制，当有许可可用时，pack()方法会立即返回，否则阻塞;
 * unpack()方法会释放一个许可给当前线程;
 * 每个线程最多拥有一个许可;
 * Created by Administrator on 2017/3/14.
 */
public class LockSupportDemo implements Runnable {
    static Object o = new Object();

    public static void main(String[] args) {
        try {
            Thread t1 = new Thread(new LockSupportDemo(), "t1");
            Thread t2 = new Thread(new LockSupportDemo(), "t2");
            t1.start();
            t2.start();
            LockSupport.unpark(t1);
            LockSupport.unpark(t2);
            System.out.println("main unpark t1 and t2.");


            t1.join();
            t2.join();
            System.out.println("main thread finished.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void run() {
        synchronized (o) {
            try {
                System.out.println(Thread.currentThread().getName() + " in.");
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " packed.");
                LockSupport.park();
                System.out.println(Thread.currentThread().getName() + " out.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
