package com.xytyzgf.concurrency.ThreadControl;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/3/13.
 */
public class CountDownLatchDemo implements Runnable {
    //声明一个倒计时器，模拟火箭发射的各项准备工作倒计时
    static CountDownLatch latch = new CountDownLatch(10);
    static CountDownLatchDemo demo = new CountDownLatchDemo();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(10);
        System.out.println("等待检查");
        for (int i = 0; i < 20; i++) {
            es.submit(new CountDownLatchDemo());
        }
        latch.await();
        System.out.println(System.currentTimeMillis() + " main  火箭发射");
    }

    public void run() {
        int waitTime = new Random().nextInt(10) * 1000;
        try {
            long currentTime = System.currentTimeMillis();
            long threadId = Thread.currentThread().getId();
            Thread.sleep(waitTime);
            System.out.println(currentTime + "  " + threadId + "  完成检查工作");
            latch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
