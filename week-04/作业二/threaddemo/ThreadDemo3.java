package com.larch.demo.threaddemo;

import com.larch.demo.threaddemo.atomic.calculate;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author ll
 * @create 2021-04-10 15:24
 */
public class ThreadDemo3 {
    public static void main(String[] args) throws Exception  {
        FutureTask futureTask = new FutureTask(new Callable() {
            public Object call() {
                return calculate.sum();
            }
        });
        Thread thread = new Thread(futureTask);
        thread.start();
        System.out.println("线程返回计算结果："+futureTask.get().toString());
        System.out.println("main线程结束....");
    }
}
