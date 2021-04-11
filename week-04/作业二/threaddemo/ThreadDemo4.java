package com.larch.demo.threaddemo;

import com.larch.demo.threaddemo.atomic.calculate;

import java.util.concurrent.*;

/**
 * @author ll
 * @create 2021-04-10 15:28
 */
public class ThreadDemo4 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Integer> submit = executorService.submit(new Callable<Integer>() {
            public Integer call() throws Exception {
                return calculate.sum();
            }
        });

        while (true) {
            if (submit.isDone()) {
                try {
                    System.out.println("计算结果返回值："+submit.get().toString());
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    executorService.shutdown();
                }
            }
        }

        System.out.println("main线程结束.....");
    }
}
