package com.longrise.study.dxc.valatile;

import java.util.concurrent.TimeUnit;

/**
 * 无volatile修饰变量
 */
public class ValatileNo {
    private static Boolean flag = true;

    public static void main(String[] args) {
        // 线程A, 判断其他线程修改该flag之后, 数据是否对本线程有效
        new Thread(() -> {
            while (flag) {
                System.out.println("A");
            }
            System.out.printf("**** %s 线程执行结束. ****", Thread.currentThread().getName());
        }, "A").start();
        
        // 线程B, 修改该flag值
        new Thread(() -> {
            try {
                // 避免 B 线程比 A 线程先运行修改 flag 值
                TimeUnit.SECONDS.sleep(1);
                flag = false;

                // 如果 flag 值修改该后, 让 B 线程先打印信息
                TimeUnit.SECONDS.sleep(2);
                System.out.printf("**** %s 线程执行结束. ****", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
        }, "B").start();
    }
}

/**
 * 执行结果如下:
 * **** B 线程执行结束. ****
 * 
 * 分析: 当 B 线程执行结束后, flag = false 并未对 A 线程生效, A 线程进入了死循环
 */