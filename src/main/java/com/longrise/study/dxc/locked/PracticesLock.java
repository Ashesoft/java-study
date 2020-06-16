package com.longrise.study.dxc.locked;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 练习: 程序按序交替 编写一个程序, 开启3个线程, 这三个线程的ID分别为A, B, C. 每个线程将自己的ID在控制台打印10遍 要求:
 * 输出的结果必须是按顺序显示, 如:ABCABCABC...
 */
public class PracticesLock {
    public static void main(String[] args) {
        AlternateDemo ad = new AlternateDemo();

        new Thread(new Runnable() {
            public void run() {
                for (int i = 1; i < 20; i++) {
                    ad.loopA(i);
                }
            }
        }, "A").start();

        new Thread(new Runnable() {
            public void run() {
                for (int i = 1; i < 20; i++) {
                    ad.loopB(i);
                }
            }
        }, "B").start();

        new Thread(new Runnable() {
            public void run() {
                for (int i = 1; i < 20; i++) {
                    ad.loopC(i);
                    System.out.println("---");
                }
            }
        }, "C").start();
    }
}

class AlternateDemo {
    private int number = 1; // 当前正在执行线程的标记
    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    public void loopA(int totalLoop) {
        // 上锁
        lock.lock();

        try {
            // 1. 判断
            if (number != 1) {
                condition1.await(); // 导致当前线程等到唤醒信号或中断
            }

            // 2. 打印
            System.out.print(Thread.currentThread().getName());

            // 3. 唤醒线程B
            number = 2;
            condition2.signal(); // 唤醒一个等待的线程
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放锁
            lock.unlock();
        }
    }

    public void loopB(int totalLoop) {
        // 上锁
        lock.lock();

        try {
            // 1. 判断
            if (number != 2) {
                condition2.await();
            }

            // 2. 打印
            System.out.print(Thread.currentThread().getName());

            // 3. 唤醒线程B
            number = 3;
            condition3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放锁
            lock.unlock();
        }
    }

    public void loopC(int totalLoop) {
        // 上锁
        lock.lock();

        try {
            // 1. 判断
            if (number != 3) {
                condition3.await();
            }

            // 2. 打印
            System.out.println(Thread.currentThread().getName());

            // 3. 唤醒线程B
            number = 1;
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放锁
            lock.unlock();
        }
    }
}