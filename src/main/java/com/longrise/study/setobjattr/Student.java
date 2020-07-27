package com.longrise.study.setobjattr;

import java.util.Date;

// 学生
public class Student {
    private Long pid; // 学生编号
    private String pname; // 学生姓名
    private Grade grade; // 所属班级
    private Date pattendtime; // 入学时间

    /**
     * @return the pid
     */
    public Long getPid() {
        return pid;
    }
    /**
     * @param pid the pid to set
     */
    public void setPid(Long pid) {
        this.pid = pid;
    }
    /**
     * @return the pname
     */
    public String getPname() {
        return pname;
    }
    /**
     * @param pname the pname to set
     */
    public void setPname(String pname) {
        this.pname = pname;
    }
    /**
     * @return the grade
     */
    public Grade getGrade() {
        return grade;
    }
    /**
     * @param grade the grade to set
     */
    public void setGrade(Grade grade) {
        this.grade = grade;
    }
    /**
     * @return the pattendtime
     */
    public Date getPattendtime() {
        return pattendtime;
    }
    /**
     * @param pattendtime the pattendtime to set
     */
    public void setPattendtime(Date pattendtime) {
        this.pattendtime = pattendtime;
    }
    @Override
    public String toString() {
        return String.format("Student:{id:%s, name:%s, grade:%s, attendTime:%s}", this.pid, this.pname, this.grade, this.pattendtime);
    }
}