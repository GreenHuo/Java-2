package com.larch.demo.threaddemo;

import com.larch.demo.threaddemo.atomic.calculate;

/**
 * @author ll
 * @create 2021-04-10 16:16
 */
public class ThreadDemo8 {
    private static int sum = 0;
    private static boolean isFlag = false;
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                sum = calculate.sum();
                System.out.println(Thread.currentThread().getName()+":"+sum);
                isFlag = true;
            }
        });
        thread.start();
        while (!isFlag) {
            System.out.println("等待中....");
        }
        System.out.println("main线程结束....线程返回值：" + sum);
    }
}
