package com.longrise.study.dxc.locked;

/**
 * 没有同步锁的加持, 数据访问异常
 */
public class TicketLockNo implements Runnable {

    private int tick = 50;

    @Override
    public void run() {
        while (true) {
            if(tick > 0){
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "完成售票, 余票为:" + --tick);
            }else{
                break;
            }
        }
    }
}
/**
 * 售票的结果出现了负数, 显然是不符合实际情况的
 */