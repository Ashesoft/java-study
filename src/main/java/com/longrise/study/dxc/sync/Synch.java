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
/**
 * 问题存在：多个消费者 生产者时使用if导致的虚假唤醒的问题(假死锁) 所以将if改成while
 * 
 * 这里分析以下死锁, 这里通过jstack命令查看了栈的快照信息如下(只抽取了等待状态的线程):
 *"A" #16 prio=5 os_prio=0 cpu=0.00ms elapsed=24.54s tid=0x000001ef9817e800 nid=0x3a9c in Object.wait()  [0x0000009a422ff000]
 *   java.lang.Thread.State: WAITING (on object monitor)
 *        at java.lang.Object.wait(java.base@11.0.4/Native Method)
 *        - waiting on <0x0000000089a5ba60> (a com.longrise.study.dxc.sync.Data)
 *        at java.lang.Object.wait(java.base@11.0.4/Object.java:328)
 *        at com.longrise.study.dxc.sync.Data.increment(Synch.java:32)
 *        - waiting to re-lock in wait() <0x0000000089a5ba60> (a com.longrise.study.dxc.sync.Data)
 *        at com.longrise.study.dxc.sync.Synch.lambda$0(Synch.java:8)
 *        at com.longrise.study.dxc.sync.Synch$$Lambda$1/0x0000000100060840.run(Unknown Source)
 *        at java.lang.Thread.run(java.base@11.0.4/Thread.java:834)
 *
 *"C" #18 prio=5 os_prio=0 cpu=0.00ms elapsed=24.54s tid=0x000001ef98191000 nid=0x3df8 in Object.wait()  [0x0000009a424fe000]
 *   java.lang.Thread.State: WAITING (on object monitor)
 *        at java.lang.Object.wait(java.base@11.0.4/Native Method)
 *        - waiting on <0x0000000089a5ba60> (a com.longrise.study.dxc.sync.Data)
 *        at java.lang.Object.wait(java.base@11.0.4/Object.java:328)
 *        at com.longrise.study.dxc.sync.Data.increment(Synch.java:32)
 *        - waiting to re-lock in wait() <0x0000000089a5ba60> (a com.longrise.study.dxc.sync.Data)
 *        at com.longrise.study.dxc.sync.Synch.lambda$2(Synch.java:18)
 *        at com.longrise.study.dxc.sync.Synch$$Lambda$3/0x0000000100061040.run(Unknown Source)
 *        at java.lang.Thread.run(java.base@11.0.4/Thread.java:834)
 * 
 * 从上面可以看出 线程A和线程B 在等待同一把锁的释放, 具体产生问题原因分析如下:
 * 根据打印的输出信息:
 * A=>1
 * B=>0
 * C=>1
 * B=>0
 * C=>1
 * B=>0
 * C=>1
 * B=>0
 * C=>1
 * 在线程C输出内容后唤醒其他等待的线程, num赋值为1;
 * 此时A, C三个线程都处于运行状态, B线程执行完后销毁了;
 * 在num=1, 没有其他线程将num赋值为0的时候, A, C两个线程纷纷都进入了等待状态, 就形成了看上去像是死锁了的状态(假状态, 将B线程的循环次数改为20即可)
 * 
 * 使用到的命令
 * 1. 使用jps查看Java内存的进程号
 * 2. 使用 jstack -l 进程号 查看当前进程内存快照
 * 3. 可以使用 jconsole 命令链接到当前的进程-->线程-->检测死锁
 */