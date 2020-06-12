package com.longrise.study.sjms.dlms;

/**
 * 枚举
 * 
 * JDK版本: JDK1.5起 
 * 是否Lazy初始化: 否 
 * 是否多线程安全: 是 
 * 实现难度: 易 
 * 
 * 描述: 这种实现方式还没有被广泛采用, 但这是实现单例模式的最佳方法. 它更简洁, 自动支持序列化机制, 绝对防止多次实例化. 它不仅能避免多线程同步问题, 而且还自动支持序列化机制,
 * 防止反序列化重新创建新的对象, 绝对防止多次实例化. 不过, 由于JDK1.5之后才加入enum特性, 用这种方式写不免让人感觉生疏, 在实际工作中,
 * 也很少用. 
 * 
 * ps: 不能通过reflection attack来调用私有构造方法.
 */
public enum Singleton6 {
    INSTANCE;

    public void whateverMethod() {

    }

    public void showMessage() {
        System.out.println(this.getClass().getName());
    }
}