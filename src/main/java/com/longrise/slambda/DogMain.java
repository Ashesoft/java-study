package com.longrise.slambda;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class DogMain {
    public static void main(String[] args) {
        Dog dog = new Dog();
        int eat = dog.eat(6);
        System.out.printf("还剩下狗粮%d斤.%n", eat);

        // Dog 无参构造
        Supplier<Dog> dog1 = Dog::new;
        System.out.printf("还剩下狗粮%d斤.%n%s%n", dog1.get().eat(2), "=========================");

        // Dog 有参构造
        Function<String, Dog> dog2 = Dog::new;
        int s1 = dog2.compose(i -> {
            System.out.println(i);
            return String.valueOf(i);
        }).andThen(d -> d.eat(3)).apply("旺财");
        System.out.println("s1=" + s1);
        
        Function<Integer, Dog> dog3 = Dog::new;
        String s2 = dog3.andThen(d-> Dog.bark(d.getName())).compose(i -> {
            System.out.printf("初始化的狗粮是:%s斤%n", i);
            return (int)i + 5;
        }).apply(1);
        System.out.println("s2=" + s2);

        // 注意:
        // compose 是对 apply() 方法参数进行处理并返回相同类型的值, 同时把返回值重新作为 apply 的参数进行传递操作, 最后执行 andThen 
        // 执行顺序 apply --> compose --> apply --> andThen

        // Dog 多参构造
        BiFunction<String, Integer, Dog> dog4 = Dog::new;
        System.out.printf("还剩下狗粮%d斤%n", dog4.apply("蛋卷", 20).eat(5));

    }
}