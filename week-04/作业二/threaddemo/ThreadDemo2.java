package com.larch.demo.threaddemo;

import com.larch.demo.threaddemo.atomic.calculate;

/**
 * @author ll
 * @create 2021-04-10 15:21
 */
public class ThreadDemo2 {
    private static int sum = 0;
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                sum = calculate.sum();
                System.out.println(Thread.currentThread().getName()+":"+sum);
            }
        });
        thread.start();
        thread.join();
        System.out.println("线程返回值：" + sum);
        System.out.println(Thread.currentThread().getName() + " :线程结束....");
    }
}
