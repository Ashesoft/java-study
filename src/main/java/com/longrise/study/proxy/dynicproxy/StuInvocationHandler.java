package com.longrise.study.proxy.dynicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class StuInvocationHandler<T> implements InvocationHandler {

    /**
     * invocationHandler 持有的被代理对象
     */
    T target;

    public StuInvocationHandler(T target){
        this.target = target;
    }

    /**
     * 代理处理类
     * 
     * @param proxy 代表动态代理对象
     * @param method 代表正在执行的方法
     * @param args 代表调用目标方法时传入的实参
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("代理执行" + method.getName() + "方法");
        Object result = method.invoke(this.target, args);
        return result;
    }
    
}