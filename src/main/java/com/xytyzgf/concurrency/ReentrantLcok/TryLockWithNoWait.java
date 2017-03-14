package com.xytyzgf.concurrency.ReentrantLcok;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2017/3/12.
 */
public class TryLockWithNoWait implements Runnable {
    public static ReentrantLock lock1 = new ReentrantLock();
    public static ReentrantLock lock2 = new ReentrantLock();
    int lock;

    public TryLockWithNoWait(int num) {
        this.lock = num;
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(new TryLockWithNoWait(1), "t1");
        Thread t2 = new Thread(new TryLockWithNoWait(2), "t2");
        t1.start();
        t2.start();
    }

    public void run() {
        String threadName = Thread.currentThread().getName();
        if (lock == 1) {
            for (; ; ) {
                if (lock1.tryLock()) {
                    try {
                        System.out.println(System.currentTimeMillis() + "---" + threadName + "获得锁1，并休眠1s");
                        Thread.sleep(100);
                        try {
                            if (lock2.tryLock()) {
                                System.out.println(System.currentTimeMillis() + "---" + threadName + "获得锁2");
                                return;
                            } else {
                                System.out.println(System.currentTimeMillis() + "---" + threadName + "未获得锁2！");
                            }
                        } finally {
                            if (lock2.isHeldByCurrentThread()) {
                                lock2.unlock();
                                System.out.println(System.currentTimeMillis() + "---" + threadName + "完成任务并释放锁2");
                            }
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        if (lock1.isHeldByCurrentThread()) {
                            lock1.unlock();
                            System.out.println(System.currentTimeMillis() + "---" + threadName + "完成任务并释放锁1");
                        }
                    }
                } else
                    System.out.println(System.currentTimeMillis() + "---" + threadName + "未获得锁1！");

            }

        } else {
            for (; ; ) {
                if (lock2.tryLock()) {
                    try {
                        System.out.println(System.currentTimeMillis() + "---" + threadName + "获得锁2，并休眠1s");
                        Thread.sleep(100);
                        try {
                            if (lock1.tryLock()) {
                                System.out.println(System.currentTimeMillis() + threadName + "获得锁1");
                                return;

                            } else {
                                System.out.println(System.currentTimeMillis() + "---" + threadName + "未获得锁1！");
                            }
                        } finally {
                            if (lock1.isHeldByCurrentThread()) {
                                lock1.unlock();
                                System.out.println(System.currentTimeMillis() + "---" + threadName + "完成任务并释放锁1");
                            }
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        if (lock2.isHeldByCurrentThread()) {
                            lock2.unlock();
                            System.out.println(System.currentTimeMillis() + "---" + threadName + "完成任务并释放锁2");
                        }
                    }
                } else
                    System.out.println(System.currentTimeMillis() + "---" + threadName + "未获得锁！");


            }

        }

    }
}
