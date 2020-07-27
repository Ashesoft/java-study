package com.longrise.study.setobjattr;

// 班级
public class Grade {
    private Long gid; // 年级编号
    private String gname; // 年级名称
    private School school; // 所属学校

    /**
     * @return the gid
     */
    public Long getGid() {
        return gid;
    }

    /**
     * @param gid the gid to set
     */
    public void setGid(Long gid) {
        this.gid = gid;
    }

    /**
     * @return the gname
     */
    public String getGname() {
        return gname;
    }

    /**
     * @param gname the gname to set
     */
    public void setGname(String gname) {
        this.gname = gname;
    }

    /**
     * @return the school
     */
    public School getSchool() {
        return school;
    }

    /**
     * @param school the school to set
     */
    public void setSchool(School school) {
        this.school = school;
    }

    @Override
    public String toString() {
        return String.format("Grade:{gid:%s, gname:%s, school:%s}", this.gid, this.gname, this.school);
    }
}