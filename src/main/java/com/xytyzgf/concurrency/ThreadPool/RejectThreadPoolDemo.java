package com.xytyzgf.concurrency.ThreadPool;

import java.util.concurrent.*;

/**
 * 线程拒绝示例
 * Created by Administrator on 2017/3/14.
 */
public class RejectThreadPoolDemo implements Runnable {

    public static void main(String[] args) throws InterruptedException {
        RejectThreadPoolDemo rj = new RejectThreadPoolDemo();
        ExecutorService es = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(10), Executors.defaultThreadFactory(),
                new RejectedExecutionHandler() {
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        System.out.println(r.toString() + " has discard.");
                    }
                }
        );
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            es.submit(rj);
            Thread.sleep(10);
        }
    }

    public void run() {
        try {
            String threadName = String.valueOf(Thread.currentThread().getId());
            System.out.println(threadName + " has running.");
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
