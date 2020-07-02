package com.longrise.study.proxy.dynicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import com.longrise.study.proxy.Person;
import com.longrise.study.proxy.Student;

public class DynicProxyMain {
    public static void main(String[] args) {
        //创建一个实例对象，这个对象是被代理的对象
        Person zs = new Student("林浅");
    
        //创建一个与代理对象相关联的InvocationHandler
        InvocationHandler stuHandler = new StuInvocationHandler<Person>(zs);
    
        //创建一个代理对象stuProxy来代理 zs, 代理对象的每个执行方法都会替换执行 Invocation 中的 invoke 方法
        Person stuProxy = (Person) Proxy.newProxyInstance(Person.class.getClassLoader(), new Class<?>[]{Person.class}, stuHandler);
    
        //代理执行交作业的方法
        stuProxy.giveTask();
    }
}