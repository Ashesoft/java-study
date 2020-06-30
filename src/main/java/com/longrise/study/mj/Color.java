package com.longrise.study.mj;

public enum Color {
    RED("红色", 1), GREEN("绿色", 2), WHITE("白色", 3), YELLOW("黄色", 4), BLUE("蓝色", 5);

    private String name;
    private int id;

    private Color(final String name, final int id) {
        this.name = name;
        this.id = id;
    }

    public void show(){
        System.out.println(this.id + ":" + this.name);
    }
}