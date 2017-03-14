package com.xytyzgf.concurrency.CyclicBarrier;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by Administrator on 2017/3/14.
 */
public class CyclicBarrierDemo {


    public static void main(String[] args) {
        final int n = 10;
        boolean flag = false;
        Thread[] threads = new Thread[n];
        CyclicBarrier cb = new CyclicBarrier(n, new Soldier.BarrierRun(flag, n));
        //设置屏障点
        flag = true;
        System.out.println("队伍集合");
        for (int i = 0; i < n; i++) {
            System.out.println("士兵" + i + "报道");
            threads[i] = new Thread(new Soldier("士兵" + i, cb));
            threads[i].start();

        }
    }

    public static class Soldier implements Runnable {
        final CyclicBarrier cb;
        String soldierName;

        public Soldier(String soldierName, CyclicBarrier cb) {
            this.soldierName = soldierName;
            this.cb = cb;
        }

        public void run() {
            try {
                //等待所有士兵到期
                cb.await();
                doWork();
                //再次调用await，执行下一次循环计数
                cb.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        public void doWork() {
            try {
                long random = new Random().nextInt(10);
                Thread.sleep(random);
                System.out.println(soldierName + " 任务完成.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public static class BarrierRun implements Runnable {
            boolean flag;
            int num;

            public BarrierRun(boolean flag, int num) {
                this.flag = flag;
                this.num = num;
            }

            public void run() {
                if (flag) {
                    System.out.println("leader: " + num + " soldiers mission complete!");
                } else {
                    System.out.println("leader: " + num + " soldiers has ready!");
                }
            }
        }

    }


}
