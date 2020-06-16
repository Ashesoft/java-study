package com.longrise.study.dxc.cas;

/**
 * 模拟cas算法
 */
public class VirtualCompareAndSwap {
    private int value;

    // 获取内存值
    public synchronized int get(){
        return value;
    }

    // 无论更新成功与否, 都返回修改之前的内存值
    public synchronized int compareAndSwap(int expectedValue, int newValue){
        // 获取旧值
        int oldValue = value;

        if(oldValue == expectedValue){
            this.value = newValue;
        }
        // 返回修改之前的值
        return oldValue;
    }

    public synchronized boolean compareAndSet(int expectedValue, int newValue){
        return expectedValue == compareAndSwap(expectedValue, newValue);
    }
}