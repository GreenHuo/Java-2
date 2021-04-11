package com.larch.demo.threaddemo;

import com.larch.demo.threaddemo.atomic.calculate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.LockSupport;

/**
 * @author ll
 * @create 2021-04-10 15:45
 */
public class ThreadDemo6 {
    private static int sum = 0;
    public static void main(String[] args) throws Exception {
        final Thread main = Thread.currentThread();
        Thread thread = new Thread(new Runnable() {
            public void run() {
                sum = calculate.sum();
                System.out.println(Thread.currentThread().getName()+":"+sum);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LockSupport.unpark(main);
            }
        });
        thread.start();
        LockSupport.park();
        System.out.println("线程返回值：" + sum);
        System.out.println(Thread.currentThread().getName() + " :线程结束....");
    }
}
