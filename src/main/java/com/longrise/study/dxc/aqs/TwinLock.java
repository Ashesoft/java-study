package com.longrise.study.dxc.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 抽象队列同步器(AQS)使用:
 * 
 * 实现一个同一时刻最多只支持两个线程同时执行的同步器
 */
public class TwinLock implements Lock {

    // 定义锁允许的最大线程数
    private final static int DEFAULT_SYNC_COUNT = 2;
    
    // 创建一个锁对象, 用以进行线程同步, Sync继承自AQS
    private final Sync sync = new Sync(DEFAULT_SYNC_COUNT);

    // 以内部类的形式实现一个同步器, 也就是锁, 这个锁继承自AQS
    private static final class Sync extends AbstractQueuedSynchronizer {
       
        private static final long serialVersionUID = 1L;

        // 构造方法中指定锁支持的线程数量
        protected Sync(int count) {

            // 若count小于0, 则默认为2
            if (count <= 0) {
                count = DEFAULT_SYNC_COUNT;
            }
            // 设置初始同步状态
            super.setState(count);
        }

        /**
         * 重写tryAcquireShared方法, 这个方法用来修改同步状态state, 也就是获取锁
         */
        @Override
        protected int tryAcquireShared(int arg) {

            // 循环尝试
            while (true) {

                // 获取当前的同步状态
                int nowState = super.getState();

                /**
                 * 计算当前线程获取锁后, 新的同步状态
                 * 注意这里使用了减法, 因为此时的state表示的是还能支持多少个线程
                 * 而当前线程如果获得了锁, 则state就要减小
                 */
                int newState = nowState - arg;

                /**
                 * 如果newState小于0, 表示当前已经没有剩余的资源了
                 * 则当前线程不能获取锁, 此时将直接返回小于0的newState
                 * 
                 * 或者newState大于0, 就会执行compareAndSetState方法修改state的值
                 * 若修改成功, 将返回大于0的newState
                 * 若修改失败, 则表示有其他线程也在尝试修改state, 此时循环一次后, 再次尝试
                 */
                if (newState < 0 || super.compareAndSetState(nowState, newState)) {
                    return newState;
                }
            }
        }

        /**
         * 尝试释放同步状态
         */
        @Override
        protected boolean tryReleaseShared(int arg) {
            while (true) {

                // 获取当前同步状态
                int nowState = super.getState();

                /**
                 * 计算释放后的新同步状态, 这里使用加法
                 * 表示有线程释放锁后, 当前锁可以支持的线程数量增加了
                 */
                int newState = nowState + arg;

                // 使用CAS修改同步状态, 若成功则返回true, 否则自旋
                if (super.compareAndSetState(nowState, newState)) {
                    return true;
                }
            }
        }

        /**
         * 执行不公平的tryLock
         */
        protected boolean tryLock(int arg) {
            Thread thread = Thread.currentThread();
            int oldState = super.getState();
            if (oldState == 0) {
                if (super.compareAndSetState(0, arg)) {
                    super.setExclusiveOwnerThread(thread);
                    return true;
                }
            } else {
                int newState = oldState + arg;
                if (newState < 0) {
                    throw new Error("超过最大锁数");
                } else {
                    super.setState(newState);
                    return true;
                }
            }
            return false;
        }

        /**
         * 获取与Lock(锁对象)实例一起使用的Condition(条件对象)实例
         * @return 条件对象
         */
        protected ConditionObject newCondition(){
            return new ConditionObject();
        }
    }

    /**
     * 获取锁
     */
    @Override
    public void lock() {
        /**
         * 这里调用的是AQS的模板方法acquireShared
         * 在acquireShared中将调用我们重写的tryAcquireShared方法
         * 传入参数为1表示当前线程, 获取到锁后, state将减1
         */
        sync.acquireShared(1);
    }

    /**
     * 以独占的方式获取锁, 如果中断, 中止;
     * 
     * 首先检查中断状态, 然后至少调用一次tryAcquire(int)方法, 成功就返回, 否则线程排队
     * 这可能会重复阻塞和解除阻塞, 调用tryAcquire(int)方法直到成功或线程中断
     */
    @Override
    public void lockInterruptibly() throws InterruptedException {
        /**
         * 这里调用的是AQS的模板方法
         */
        sync.acquireInterruptibly(1);
    }

    /**
     * 尝试获取锁
     * 
     * 如果没有其他线程持有该锁, 则获取该锁, 并立即返回true值, 将锁保持计数设置为1;
     * 即使已将此锁设置为使用公平的排序策略, 对tryLock()方法的调用也会立即获取该锁(如果可用),无论其他线程当前是否在等待该锁;
     * 即使破坏公平性, 这种"讨价还价"的行为在某些情况下还是有用的;
     * 如果要遵守此锁的公平性设置, 就使用几乎等效的tryLock（0，TimeUnit.SECONDS）（它还会检测到中断）
     */
    @Override
    public boolean tryLock() {
        return sync.tryLock(1);
    }

    /**
     * 尝试以独占模式获取锁, 如果中断则中止, 如果超过给定的时间则失败
     * 
     * 首先通过检查中断状态,然后至少调用一次tryAcquire来实现, 并在成功后返回, 否则将排队
     * 这可能会反复阻塞和解除阻塞, 调用tryAcquire直到成功或线程被中断或超时为止;
     */
    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    /**
     * 尝试释放锁
     * 
     * 如果当前线程是此锁的持有者, 则保留计数将减少;
     * 如果保持计数现在为0, 则释放锁;
     * 如果当前线程不是此锁的持有者, 则抛出"非法的监视器状态异常"的异常
     */
    @Override
    public void unlock() {
        /**
         * 这里调用的是AQS的模板方法releaseShared
         * 在releaseShared中将调用我们重写的tryReleaseShared方法
         * 传入参数为1表示当前线程, 释放掉锁后, state将加1
         */
        sync.releaseShared(1);
    }

    /**
     * 获取与Lock(锁对象)实例一起使用的Condition(条件对象)实例
     * 
     * 当与内置监视器锁定一起使用时, 返回的Condition实例支持与Object监视器方法(wait, notify, notifyAll)相同的用法
     * 如果在调用任何条件等待或信令方法时未保持此锁定, 则将抛出"非法的监视器状态异常"的异常
     * 调用条件等待方法时, 将事发该锁, 并在它们返回之前, 重新获取该锁, 并将锁保持计数恢复到调用该方法时的状态
     * 如果线程在等待时被中断, 则等待将终止, 同时抛出线程中断异常, 并清除线程的中断状态, 等待线程以先进先出顺序发出信号
     * 从等待方法返回的线程的锁重新获取顺序与最初获取锁的线程的顺序相同(默认情况下未指定), 但对于公平锁, 优先使用等待时间最长的线程
     * 
     */
    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }

}