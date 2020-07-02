package com.longrise.study.proxy.staticproxy;

import com.longrise.study.proxy.Person;
import com.longrise.study.proxy.Student;

/**
 * 静态代理
 * 
 * 学生代理类, 也实现了 Person 接口, 保存一个学生实体, 这样就可以代理学生产生行为
 */
public class StudentProxy implements Person {

    // 被代理的学生
    private Student stu;

    public StudentProxy(Person stu){
        // 代理学生行为
        this.stu = (Student) stu;
    }

    /**
     * 代理叫作业, 调用被代理学生交作业的行为
     */
    @Override
    public void giveTask() {
        this.stu.giveTask();
    }
    
}