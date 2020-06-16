package com.longrise.study.dxc.valatile;

import java.util.concurrent.TimeUnit;

/**
 * 有 volatile 修饰
 */
public class ValatileYes {
    private static volatile Boolean flag = true;

    public static void main(String[] args) {

        // A 线程，判断其他线程修改 flag 之后，数据是否对本线程有效
        new Thread(() -> {
            while (flag) {
                System.out.println("A");
            }
            System.out.printf("********** %s 线程执行结束！**********", Thread.currentThread().getName());
        }, "A").start();

        // B 线程，修改 flag 值
        new Thread(() -> {
            try {
                // 避免 B 线程比 A 线程先运行修改 flag 值
                TimeUnit.SECONDS.sleep(1);
                flag = false;
                // 如果 flag 值修改后，让 B 线程先打印信息
                TimeUnit.SECONDS.sleep(2);

                System.out.printf("********** %s 线程执行结束！**********", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "B").start();

    }
}
/**
 * B 线程修改 flag 值后，对 A 线程数据有效，A 线程跳出循环，执行完成。所以 volatile 修饰的变量，有新值写入后，对其他线程来说，数据是有效的，能被其他线程读到。
 */