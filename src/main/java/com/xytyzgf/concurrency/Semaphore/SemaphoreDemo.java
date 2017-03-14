package com.xytyzgf.concurrency.Semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by Administrator on 2017/3/13.
 */
class SemaphoreDemo implements Runnable {
    static Semaphore semaphore = new Semaphore(5);

    public static void main(String[] args) {
        //线程池
        ExecutorService es = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 20; i++) {
            es.submit(new SemaphoreDemo());
        }
    }

    public void run() {
        try {
            semaphore.acquire();
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getId());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }

    }
}
