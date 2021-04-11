package com.larch.demo.threaddemo.atomic;

/**
 * @author ll
 * @create 2021-04-10 15:15
 */
public class calculate {

    public static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }
}
