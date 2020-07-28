package com.longrise.study.proxy.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 介绍:
 * CGLIB 是一个功能强大, 高性能的代码生成包. 它为没有实现接口的类提供代理, 为 JDK 的动态代理提供了很好的补充.
 * 通常可以使用 Java 的动态代理创建代理, 但当要代理的类没有实现接口或者为了更好的性能, 它是一个好的选择.
 * 
 * 原理:
 * 动态生成一个要代理类的子类, 子类重写要代理的类的所有不是 final 的方法. 在子类中采用方法拦截的技术拦截所有父类方法的调用, 顺势植入横切逻辑.
 * 它比使用 Java 反射的 JDK 动态代理要快.
 * 底层:
 * 使用字节码处理框架 ASM, 来转换字节码并生成新的类. 不鼓励直接使用 ASM, 因为它要求你必须对 JVM 内部结构包括 class 文件的格式和指令集都很熟悉.
 * 缺点:
 * 对于 final 方法, 无法进行代理
 */
public class CglibProxy implements MethodInterceptor {
    private Object target;

    public CglibProxy(Object target){
        this.target = target;
    }

    /**
     * 代理处理类
     * 
     * @param proxy       代理对象
     * @param method      代理类方法
     * @param args        方法参数
     * @param methodProxy 方法代理
     * @return 方法执行后的返回值
     */
    @Override
    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        if (this.connect()) {
            Object returnData = method.invoke(this.target, args);
            this.close();
            return returnData;
        }
        return null;
    }

    public boolean connect() {
        System.out.println("建立连接");
        return true;
    }

    public void close() {
        System.out.println("关闭连接");
    }

}