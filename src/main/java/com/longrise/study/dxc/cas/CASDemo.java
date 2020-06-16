package com.longrise.study.dxc.cas;

public class CASDemo {
    public static void main(String[] args) {
        final VirtualCompareAndSwap vAndSwap = new VirtualCompareAndSwap();

        for (int i = 0; i < 10; i++) {
            // 创建10个线程, 模拟多线程环境
            new Thread(()->{
                int expectedValue = vAndSwap.get();
                boolean bool = vAndSwap.compareAndSet(expectedValue, (int)(Math.random() * 100));
                System.out.println(bool);
            }).start();
        }
    }
}