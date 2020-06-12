package com.longrise.study.sjms.dlms;

/**
 * 登记式/静态内部类
 * 
 * 是否Lazy初始化: 是 
 * 是否多线程安全: 是 
 * 实现难度: 一般 
 * 
 * 描述: 这种方式能达到双检锁方式一样的功效, 但实现更简单. 对静态域使用延迟初始化,
 * 应使用这种方式而不是双检锁方式. 这种方式之适用于静态域的情况, 双检锁方式可在实例域需要延迟初始化时 使用. 这种方式同样利用了class
 * loader机制来保证初始化instance时只有一个线程, 它跟第3种方式不同的是: 
 * 
 * 第3种方式只要Singleton3类被装载了, 那么instance就会被实例化(没有达到lazy loading效果), 而这种方式是Singleton5类被装载了, instance不一定被初始化.
 * 因为SingletonHolder类没有被 主动使用, 只有通过显示调用getInstance方式时, 才会显示装载SingletonHolder类,
 * 从而实例化instance. 想象一下, 如果实例化instance很消耗资源, 所以想让它延迟加载, 另外一方面, 又不希
 * 望在Singleton5加载时就实例化, 因为不能确保Singleton5类还可能在其他的地方被主动使用从而被加载,
 * 那么这个时候实例化instance显然是不合适的. 这个时候, 这种方式相比第3种方式就显得很合理.
 */
public class Singleton5 {
    private static class SingletonHolder {
        private static final Singleton5 INSTANCE = new Singleton5();
    }

    private Singleton5() {

    }

    public static final Singleton5 getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void showMessage() {
        System.out.println(this.getClass().getName());
    }
}