package com.longrise.stream;

// 学生类
public class Student {
    private String name;
    private int age;
    private char sex;
    private String grade;

    public Student(){}
    public Student(String name, int age, char sex, String grade) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.grade = grade;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }
    /**
     * @return the sex
     */
    public char getSex() {
        return sex;
    }
    /**
     * @return the grade
     */
    public String getGrade() {
        return grade;
    }
    @Override
    public String toString() {
        return String.format("{name:%s, age:%s, sex:%s, grade:%s}%n", this.name, this.age, this.sex, this.grade);
    }
}