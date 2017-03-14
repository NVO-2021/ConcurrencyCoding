package com.xytyzgf.concurrency.BaseThreadOperate;

/**
 * Created by Administrator on 2017/3/10.
 */
public class BadSuspend {
    public static Object o = new Object();
    static Thread t1 = new Thread(new ChangeObjectThread("t1"));
    static Thread t2 = new Thread(new ChangeObjectThread("t2"));

    public static void main(String[] args) throws InterruptedException {
        t2.setName("t2");
        t1.setName("t1");
        t2.start();
        t1.start();
        System.out.println("main thread has resume thread 1 and 2.");
        t1.resume();
        t2.resume();
//        t1.join();
//        t2.join();
        System.out.println(System.currentTimeMillis());

        System.out.println(System.currentTimeMillis());


//        Thread.sleep(5000);

        System.out.println("Thread-main has finished.");
    }

    public static class ChangeObjectThread implements Runnable {
        public String name;

        public ChangeObjectThread(String name) {
            setName(name);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void run() {
            synchronized (o) {
                System.out.println(getName() + " in");
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                System.out.println("Thread " + getName() + "has suspended.");
                Thread.currentThread().suspend();
                System.out.println(getName() + " finished.");
            }
        }
    }
}
