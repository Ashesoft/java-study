package com.longrise.study.dxc.sync;

public class Synch {
    public static void main(String[] args) {
        Data data = new Data();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.increment();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.decrement();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.increment();
            }
        }, "C").start();
    }
}

// 判断等待 业务 通知
class Data {
    private int num = 0;

    public synchronized void increment() {
        // if (num != 0) {
        while (num != 0) {
            try {
                wait(); // 等待
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        num++;
        System.out.println(Thread.currentThread().getName() + "=>" + num);
        // 唤醒所有再等待的线程
        notifyAll();
    }

    public synchronized void decrement() {
        // if (num == 0) {
        while (num == 0) {
            try {
                wait(); // 等待
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        num--;
        System.out.println(Thread.currentThread().getName() + "=>" + num);
        // 唤醒所有再等待的线程
        notifyAll();
    }
}
/**
 * 问题存在：多个消费者 生产者时使用if导致的虚假唤醒的问题(死锁) 所以将if改成while
 */