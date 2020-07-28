package com.longrise.study.annotation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MessageProxy implements InvocationHandler {
    private Object target;

    public Object bind(Object target){
        this.target = target;
        return Proxy.newProxyInstance(this.target.getClass().getClassLoader(), this.target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try{
            if(this.connect()){
                return method.invoke(this.target, args);
            }else{
                throw new RuntimeException("[error]无法进行消息发送");
            }
        }finally{
            this.close();
        }
    }

    private boolean connect(){
        System.out.println("[建立消息发送通道]");
        return true;
    }
    private void close(){
        System.out.println("[关闭消息发送通道]");
    }
}