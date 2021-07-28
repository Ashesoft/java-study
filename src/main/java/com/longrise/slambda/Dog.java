package com.longrise.slambda;

public class Dog {
    private String name = "哮天犬"; // 狗名
    private int food = 10; // 狗粮

    // 默认构造
    public Dog() {

    }

    // 有参构造
    public Dog(String name) {
        System.out.println("初始化了狗名");
        this.name = name;
    }

    // 有参构造
    public Dog(int food) {
        System.out.println("初始化了狗粮:" + food);
        this.food = food;
    }

    public Dog(String name, int food) {
        this.name = name;
        this.food = food;
        System.out.println(this);
    }

    public static String bark(String name) {
        return String.format("%s汪汪叫了", name);
    }

    // 吃狗粮
    public int eat(int eatNum) {
        System.out.printf("%s吃了%d斤狗粮%n", this.name, eatNum);
        this.food -= eatNum;
        return this.food;
    }

    /**
     * @return the food
     */
    public int getFood() {
        return this.food;
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return String.format("[name=%s, food=%d]", this.name, this.food);
    }
}