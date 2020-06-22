package com.longrise.study.dxc.aqs;

import java.util.concurrent.locks.Lock;

public class TwinMain {
    public static void main(String[] args) {

        // 创建一个自定义的锁对象
        Lock lock = new TwinLock();

        // 启动10个线程去尝试获取锁
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                
                // 循环执行
                while (true) {

                    // 获取锁
                    lock.lock();
                    try {

                        // 休眠1秒
                        Thread.sleep(1000);
                        
                        // 输出线程名称
                        System.out.println(Thread.currentThread().getName());
                        
                        // 再次休眠1秒
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        // 释放锁
                        lock.unlock();
                    }
                }
            });

            // 将线程设置为守护线程, 主线程结束后, 守护线程自动结束
            thread.setDaemon(true);
            thread.start();
        }

        // 主线程每隔1秒输出一个分割线
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("*******main******");
        }
    }
}