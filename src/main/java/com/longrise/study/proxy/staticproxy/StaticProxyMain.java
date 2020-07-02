package com.longrise.study.proxy.staticproxy;

import com.longrise.study.proxy.Person;
import com.longrise.study.proxy.Student;

public class StaticProxyMain {
    public static void main(String[] args) {

        // 被代理的学生张三, 他的作业上交由代理对象 monitor 完成
        Person zs = new Student("张三");

        // 生成代理对象, 并将张三传给代理对象
        Person monitor = new StudentProxy(zs);

        // 班长代理交作业
        monitor.giveTask();
    }
}