package com.larch.demo.threaddemo;

import com.larch.demo.threaddemo.atomic.calculate;

/**
 * @author ll
 * @create 2021-04-10 15:03
 */
public class ThreadDemo1 {
    private static int sum = 0;
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                sum = calculate.sum();
                System.out.println(Thread.currentThread().getName()+":"+sum);
            }
        });
        thread.start();
        while (sum == 0) {
            Thread.sleep(100);
        }
        System.out.println("线程返回值：" + sum);
        System.out.println("main线程结束....");
    }
}
