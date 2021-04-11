package com.larch.demo.threaddemo;

import com.larch.demo.threaddemo.atomic.calculate;

import java.util.concurrent.CountDownLatch;

/**
 * @author ll
 * @create 2021-04-10 15:40
 */
public class ThreadDemo5 {
    private static int sum = 0;
    public static void main(String[] args) throws Exception {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        Thread thread = new Thread(new Runnable() {
            public void run() {
                sum = calculate.sum();
                System.out.println(Thread.currentThread().getName()+":"+sum);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            }
        });
        thread.start();
//        thread.join();
        countDownLatch.await();
        System.out.println("线程返回值：" + sum);
        System.out.println(Thread.currentThread().getName() + " :线程结束....");
    }
}
