package com.xytyzgf.concurrency.BaseThreadOperate;

/**
 * Created by Administrator on 2017/3/12.
 */
public class InterruptTest extends Thread {
    private Thread parent;

    public InterruptTest(Thread parent) {
        this.parent = parent;
    }

    public static void main(String[] args) {
        InterruptTest t = new InterruptTest(Thread.currentThread());
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            System.out.println(e.getCause());
            System.out.println("Parent thread will die...");
        }
    }

    public void run() {
        while (true) {
            System.out.println("sub thread is running...");
            long now = System.currentTimeMillis();
            while (System.currentTimeMillis() - now < 10000) {
                // 为了避免Thread.sleep()而需要捕获InterruptedException而带来的理解上的困惑,
                // 此处用这种方法空转2秒
            }
            parent.interrupt();
        }
    }
}

