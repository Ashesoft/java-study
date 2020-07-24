package com.longrise.study.setobjattr;

import com.longrise.study.setobjattr.utils.BeanUtils;

public class TMain {

    public void test1() {
        School school = new School(10010001L, "帝都京师一中", "帝都爆米花一街2号");
        System.out.println(school);
    }

    public void test2() {
        String info = "sid=1001001|sname=帝都京师五中|saddr=帝都爆米花五街8号|screatetime=2020-07-07 17:29:29";
        School school = BeanUtils.initBean1(School.class, info);
        System.out.println(school);
    }


    public static void main(String[] args) {
        //new TMain().test1();
        new TMain().test2();
    }
}