package com.longrise.study.setobjattr;

import com.longrise.study.setobjattr.utils.BeanUtils;

public class TMain {

    // 使用 new 关键初始化对象
    public void test1() {
        School school = new School(10010001L, "帝都京师一中", "帝都爆米花一街2号");
        System.out.println(school);
    }

    // 使用反射进行实例化对象并对其单属性赋值
    public void test2() {
        String info = "sid=1001001|sname=帝都京师五中|saddr=帝都爆米花五街8号|screatetime=2020-07-07 17:29:29";
        School school = BeanUtils.createBean(School.class, info);
        System.out.println(school);
    }

    // 使用反射进行实例化对象并对其进行级联属性赋值
    public void test3() {
        // 学生信息
        String str = "pid=100610|pname=程序猿|grade.gid=10007|grade.gname=帝都一中2班|grade.school.sid=1001002|grade.school.sname=帝都京师第一中学|grade.school.saddr=帝都爆米花五街8号|grade.school.screatetime=2010-08-08|pattendtime=2019-09-01";
        Student student = BeanUtils.createBean(Student.class, str);
        System.out.println(student);
    }

    public static void main(String[] args) {
        TMain tMain = new TMain();
        tMain.test1();
        tMain.test2();
        tMain.test3();
    }
}