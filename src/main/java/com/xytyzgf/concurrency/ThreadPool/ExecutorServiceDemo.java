package com.xytyzgf.concurrency.ThreadPool;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 固定大小的线程池
 * Created by Administrator on 2017/3/14.
 */
public class ExecutorServiceDemo implements Runnable {

    public static void main(String[] args) {
        int poolSize = 3;
        ExecutorService es = Executors.newFixedThreadPool(poolSize);
        for (int i = 0; i < 10; i++) {
            ExecutorServiceDemo demo = new ExecutorServiceDemo();
            es.submit(new Thread(demo, "t" + i));
        }
    }

    public void run() {
        try {
            long sleepTime = new Random().nextInt(10) * 1000;
            String threadName = Thread.currentThread().getName();
            System.out.println(System.currentTimeMillis() + " thread " + threadName + " is running.");
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
