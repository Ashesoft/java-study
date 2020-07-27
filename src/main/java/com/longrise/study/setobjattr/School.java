package com.longrise.study.setobjattr;

import java.util.Date;

// 学校
public class School {
    private long sid; // 学校编号
    private String sname; // 学校名称
    private String saddr; // 学校地址
    private Date screatetime; // 学校创建时间

    public School(){}
    
    public School(long sid, String sname, String saddr){
        this.sid = sid;
        this.sname = sname;
        this.saddr = saddr;
    }

    public long getSid() {
        return sid;
    }
    public void setSid(long sid) {
        this.sid = sid;
    }
    public String getSname() {
        return sname;
    }
    public void setSname(String sname) {
        this.sname = sname;
    }
    public String getSaddr() {
        return saddr;
    }
    public void setSaddr(String saddr) {
        this.saddr = saddr;
    }
    public Date getScreatetime() {
        return screatetime;
    }
    public void setScreatetime(Date screatetime) {
        this.screatetime = screatetime;
    }

    public String toString(){
        return String.format("School:{sid:%s, sname:%s, saddr:%s, screatetime:%s}", this.sid, this.sname, this.saddr, this.screatetime);
    }
}