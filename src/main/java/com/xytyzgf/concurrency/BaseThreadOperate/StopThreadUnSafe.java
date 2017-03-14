package com.xytyzgf.concurrency.BaseThreadOperate;

/**
 * Created by Administrator on 2017/3/10.
 *
 * @Description 本类用于描述stop()方式中断线程导致的安全问题
 */
public class StopThreadUnSafe {
    private static User u = new User();

    /**
     * 使用stop()会导致线程直接中断并立即释放持有对象的锁，导致同步模块对数据一致性的保护失效，导致数据不一致
     */
    public static void main(String[] args) throws InterruptedException {
        new Thread(new ReadVariables()).start();
        while (true) {
            Thread t = new Thread(new ChangeVariables());
            t.start();
            Thread.sleep(1200);
            t.stop();
        }
    }

    public static class User {
        static int userId1 = 0;
        static int userId2 = 0;

        public String toString() {
            return "userId1:" + userId1 + " \nuserId2:" + userId2;
        }
    }

    public static class ChangeVariables implements Runnable {
        public void run() {
            try {
                while (true) {
                    synchronized (u) {
                        System.out.println("ChangeVariables synchonized running");
                        int num = (int) System.currentTimeMillis() / 1000;
                        u.userId1 = num;
                        Thread.sleep(1000);
                        u.userId2 = num;
                        Thread.yield();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class ReadVariables implements Runnable {
        public void run() {
            try {
                while (true) {
                    synchronized (u) {
                        if (u.userId1 != u.userId2)
                            System.out.println(u.toString());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
