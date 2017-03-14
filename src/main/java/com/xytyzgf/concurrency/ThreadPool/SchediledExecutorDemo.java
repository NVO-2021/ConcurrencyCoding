package com.xytyzgf.concurrency.ThreadPool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 定时调度任务调度
 * <p>
 * Created by Administrator on 2017/3/14.
 */
public class SchediledExecutorDemo implements Runnable {

    public static void main(String[] args) {
        SchediledExecutorDemo demo = new SchediledExecutorDemo();
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(10);
        //当执行时间大于定时任务，该方法会忽略间隔直接执行下去
//        ses.scheduleAtFixedRate(demo,0,2, TimeUnit.SECONDS);
        //该方法的两个方法执行时间间隔为   前一个方法执行时间+设定等待时间
        ses.scheduleWithFixedDelay(demo, 0, 2, TimeUnit.SECONDS);
    }

    public void run() {
        try {
            long currentTime = System.currentTimeMillis() / 1000;

            Thread.sleep(3000);
            System.out.println(currentTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
