package com.longrise.study.dxc.valatile;

import java.util.concurrent.CountDownLatch;

/**
 * volatile 的原子性
 */
public class ValatileToAtomic {
    private static volatile int num = 0;

    public static void main(String[] args) {
        final CountDownLatch latch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) { // 创建10个线程
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) { // 每个线程累加 1000
                    num++;
                }
                latch.countDown();
            }, String.valueOf(i + 1)).start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 所有线程累加计算的数据
        System.out.printf("num: %d", num);
    }
}
/**
 * 上面的代码中, 如果volatile修饰num, 在num++运算中能持有原子性, 那么根据以上数量的累加, 随后应该是num:10000, 可实际的代码执行结果如下:
 * num: 8415
 * 
 * 分析: 结果与我们预计的数据相差挺多的, 虽然volatile变量在更新值的时候会通知其他线程刷新主内存中最新的数据, 但这只能保证其基本类型变量读/写的原子操作(如: num = 2).
 * 由于num++ 是属于一个非原子操作的复合操作, 所以不能保证其原子性.
 */