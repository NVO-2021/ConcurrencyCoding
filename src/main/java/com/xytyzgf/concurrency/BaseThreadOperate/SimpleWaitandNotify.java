package com.xytyzgf.concurrency.BaseThreadOperate;

/**
 * Created by Administrator on 2017/3/10.
 */
public class SimpleWaitandNotify {
    final static Object obj = new Object();

    /**
     * wait线程进入等待状态并释放对象obj的锁，
     * notify线程notify()object对象，随机唤醒了wait线程，
     * 但notify线程却没有释放obj对象的锁，
     * 因此wait线程只能不断重复等待nofify线程执行完后才能继续执行；
     *
     * @param args
     */
    public static void main(String[] args) {
        WaitClass w = new WaitClass();
        NotifyClass n = new NotifyClass();
        new Thread(w).start();
        new Thread(n).start();
    }

    public static class WaitClass implements Runnable {

        public void run() {
            System.out.println("WaitClass try to catch Object.");
            synchronized (obj) {
                System.out.println("WaitClass is start.");
                System.out.println("WaitClass wait for object.");
                try {
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("WaitClass is end in " + System.currentTimeMillis());

            }
        }
    }

    public static class NotifyClass implements Runnable {

        public void run() {

            System.out.println("NotifyClass is try to catch Obj");
            synchronized (obj) {
                System.out.println("NotifyClass is start");
                System.out.println("NotifyClass notify for object");
                obj.notify();
                System.out.println("NotifyClass is end in " + System.currentTimeMillis());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
