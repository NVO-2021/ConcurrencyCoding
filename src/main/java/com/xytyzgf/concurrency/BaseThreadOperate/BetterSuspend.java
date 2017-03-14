package com.xytyzgf.concurrency.BaseThreadOperate;

/**
 * Created by Administrator on 2017/3/10.
 */
public class BetterSuspend {


    private static Object o = new Object();

    public static void main(String[] args) throws InterruptedException {
        SmartThread c1 = new SmartThread();
        ReadObjctClass c2 = new ReadObjctClass();
        Thread t1 = new Thread(c1, "t1");
        Thread t2 = new Thread(c2, "t2");
        t1.start();
        t2.start();
        Thread.sleep(1000);
        c1.susPendThis();
        System.out.println("suspend t1 for 1 minute.");
        Thread.sleep(5000);
        System.out.println("resume t1.");
        c1.resumeThis();
    }

    public static class SmartThread implements Runnable {
        static volatile boolean suspendFlag = false;

        public void susPendThis() {
            suspendFlag = true;
        }

        public void resumeThis() {
            suspendFlag = false;
            synchronized (this) {
                notify();
            }
        }

        public void run() {
            while (true) {
                synchronized (this) {
                    while (suspendFlag) {
                        try {
//                            System.out.println(System.currentTimeMillis() + Thread.currentThread().getName() + " 正在被挂起，继续等待...");
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                synchronized (o) {
//                    System.out.println(System.currentTimeMillis() + Thread.currentThread().getName() + " 正在操作对象...");
                }
                Thread.yield();
            }

        }
    }

    public static class ReadObjctClass implements Runnable {


        public void run() {
            while (true) {
                synchronized (o) {
//                    System.out.println(System.currentTimeMillis() + Thread.currentThread().getName() + " 正在读取对象...");
                }
                Thread.yield();
            }
        }
    }
}
