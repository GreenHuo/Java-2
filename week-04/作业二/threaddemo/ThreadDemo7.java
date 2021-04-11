package com.larch.demo.threaddemo;

import com.larch.demo.threaddemo.atomic.calculate;

/**
 * @author ll
 * @create 2021-04-10 15:56
 */
public class ThreadDemo7 {
    private static int sum = 0;
    public static void main(String[] args) throws InterruptedException {
        final Object obj = new Object();
        Thread thread = new Thread(new Runnable() {
            public void run() {
                synchronized (obj) {
                    sum = calculate.sum();
                    System.out.println(Thread.currentThread().getName()+":"+sum);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    obj.notify();
                }
            }
        });
        thread.start();
        synchronized (obj) {
            obj.wait();
            System.out.println("主线程唤醒...");
        }
        System.out.println("main线程结束....线程返回值：" + sum);
    }

}
