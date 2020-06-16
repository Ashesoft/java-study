package com.longrise.study.dxc.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池
 * 
 * 线程池提供了一个线程队列, 队列中保存着所有等待状态的线程
 * 避免了创建与销毁线程的额外开销, 提高了响应速度;
 * 
 * 线程池的体系结构
 * java.util.concurrent.Executor: 负责线程的使用和调度的跟接口
 * ExecutorService: 子接口, 线程池的主要接口
 * ThreadPoolExecutor: 线程池的实现类d
 * ScheduledExecutorService: 子接口,负责线程的调度
 * ScheduledThreadPoolExecutor: 继承了线程池的实现类, 实现了负责线程调度的子接口
 * 
 * 工具类: Executors
 * ExecutorService newFixedThreadPool(): 创建固定大小的线程池
 * ExecutorService newCachedThreadPool(): 缓存线程池, 线程池中线程的数量不固定, 可以根据需求自动更改数量
 * ExecutorService newSingleThreadExecutor(): 创建单个线程池, 线程池中只有一个线程
 * ScheduledExecutorService newScheduledThreadPool(): 创建固定大小的线程, 可以延时或定时的执行任务
 */
public class ThreadPool {
    public static void main(String[] args) {
        // 创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            // 为线程池中的线程分配任务
            executorService.submit(new Runnable() {
                private int i = 0;

                @Override
                public void run() {
                    while (i <= 100) {
                        System.out.println(Thread.currentThread().getName() + ":" + i++);
                    }
                }
            });
        }
        // 关闭线程池
        executorService.shutdown();
    }
}