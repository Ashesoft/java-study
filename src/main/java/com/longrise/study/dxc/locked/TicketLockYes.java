package com.longrise.study.dxc.locked;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 有同步锁的加持, 数据访问正常
 */
public class TicketLockYes implements Runnable {

    private int tick = 50;
    private Lock lock = new ReentrantLock(false);

    @Override
    public void run() {
        while (true) {
            lock.lock();
            try {
                if (tick > 0) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "完成售票, 余票为:" + --tick);
                } else {
                    break;
                }
            } finally {
                lock.unlock();
            }
        }
    }
}
/**
 * 售票的结果出现了负数, 显然是不符合实际情况的
 */