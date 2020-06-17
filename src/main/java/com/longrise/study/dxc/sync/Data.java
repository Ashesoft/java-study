package com.longrise.study.dxc.sync;

// 判断等待 业务 通知
public class Data {
    private int num = 0;

    public synchronized void increment() {
        // if (num != 0) {
        while (num != 0) {
            try {
                this.wait(); // 等待
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        num++;
        System.out.println(Thread.currentThread().getName() + "=>" + num);
        // 唤醒所有再等待的线程
        this.notifyAll();
    }

    public synchronized void decrement() {
        // if (num == 0) {
        while (num == 0) {
            try {
                this.wait(); // 等待
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        num--;
        System.out.println(Thread.currentThread().getName() + "=>" + num);
        // 唤醒所有再等待的线程
        this.notifyAll();
    }
}