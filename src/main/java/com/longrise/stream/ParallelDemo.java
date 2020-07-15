package com.longrise.stream;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

// 并行流
public class ParallelDemo {
    public static void main(String[] args) {

        // 使用 parallel 方法创建一个并行流
        IntStream.range(1, 100).parallel().forEach(ParallelDemo::show);
        System.err.println("----------------------");

        // 能否实现先并行再串行的流 / 先串行再并行的流
        IntStream.range(1, 100).parallel().peek(ParallelDemo::debug) // 使用 parallel 方法打开并行模式, 关闭串行模式
                .sequential().forEach(ParallelDemo::show); // 使用 sequential 方法打开串行模式, 关闭并行模式
        // 结果是串行模式(顺序执行)

        System.err.println("+++++++++++++++");
        IntStream.range(1, 100).peek(ParallelDemo::debug) // 默认就是串行模式
                .parallel().forEach(ParallelDemo::show); // 使用 parallel 方法打开并行模式, 关闭串行模式
        // 结果是并行模式
        // 总结再一个流中不管是调用了 parallel(并行)方法, 还是调用了 sequential(串行)方法, 谁在最后谁就起作用

        // 对与并行流使用了默认的线程池 ForkJoinPool.commonPool
        // 默认的线程个数是当前逻辑cpu的个数

        // 通过 jdk api 文档中对于 ForkJoinPool 的介绍, 可以修改默认线程池中线程的个数, 如下
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "10"); // 设置并行并发数

        System.out.println("=============");
        // 多个并行任务使用系统默认的线程池会造成一定的阻塞, 需要对特定的任务创建特定的线程池
        // 自己定义的线程池名是ForkJoinPool-1
        ForkJoinPool pool = new ForkJoinPool(10);
        try {
            long sum = pool.submit(() -> IntStream.range(1, 100).parallel().peek(ParallelDemo::debug).sum()).get(); // 有返回值的会阻塞主线程, 直到返回结果
            System.out.println("sum = " + sum);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        pool.shutdown(); // 可能启动有序关闭，其中先前提交的任务被执行, 但不会接受任何新的任务
        
        // while (!pool.isTerminated()) {} // 如果所有任务在关闭后完成, 则返回 true
        // while (!pool.isQuiescent()){} // 如果所有工作线程当前处于空闲状态, 则返回 true
        
        // 这里需要注意的是: 创建的线程池是主线程的守护线程, 当主线程结束时, 守护线程也会结束
        // 所以需要主线程等待守护线程结束后才结束
    }

    public static void show(int i) {
        System.out.println(Thread.currentThread().getName() + " ==show==> " + i);
    }

    public static void debug(int i) {
        System.out.println(Thread.currentThread().getName() + " ==debug==> " + i);
    }
}