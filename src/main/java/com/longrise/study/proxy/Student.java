package com.longrise.study.proxy;

/**
 * 学生类, 具体行为交作业
 */
public class Student implements Person {

    private String name;
    public Student(String name){
        this.name = name;
    }

    @Override
    public void giveTask() {
        System.out.println(this.name + "交作业了");
    }
    
}