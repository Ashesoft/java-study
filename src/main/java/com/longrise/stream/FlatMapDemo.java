package com.longrise.stream;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Stream;

// 展开流
public class FlatMapDemo {
    public static void main(String[] args) {
        String str = "my name is lilei";

        // 把满足条件的字符串长度打印出来(map --> 获取 A 元素里面的 B 属性)
        Stream.of(str.split(" ")).map(s -> s.length()).filter(i -> i > 2).forEach(System.out::println);

        System.out.println("------");
        Stream.of(str.split(" ")).flatMap(s -> Stream.of(s.split(""))).forEach(System.out::println);

        // IntStream/LongStream/DoubleStream 并不是 Stream 的子类
        // 故需要用 boxed 方法将 Int/Long/DoubleStream 封装成与之对应的 Stream<Integer/Long/Double> 流
        Stream.of(str.split(" ")).flatMap(s -> s.chars().boxed()).forEach(System.out::println);

        // peek 是个中间操作, 用于 debug 时查看流状态的
        // forEach 是个终止操作, 用于输出流结果的
        System.out.println("----peek----");
        Stream.of(str.split(" ")).flatMap(s -> s.chars().boxed()).peek(System.out::println).forEach(System.out::println);

        // limit 通常使用在无限流中
        Random random = new Random();
        random.ints().limit(10).forEach(System.out::println);
        System.out.println("-------------");
        int rs = random.ints().filter(i -> i > 0 && String.valueOf(i).length() == 6).findAny().getAsInt(); // 生成6位 int 类型验证码
        System.err.println("rs=" + rs);
        rs  = random.ints(100000, 1000000).findAny().getAsInt(); // 生成6位 int 类型验证码(简单)
        System.err.println("rs=" + rs);

        // 使用 ints 方法生成 IntStream, 在通过 boxed 方法包装成 Stream<Integer>, 再通过 map 方法获取属性并包装成 Stream<String>, 最后取值(默认值000000)
        String orElseGet = random.ints().filter(i -> i > 0 && String.valueOf(i).length() == 6).boxed().map(String::valueOf).findAny().orElseGet(()->"000000"); // 生成6位 String 类型验证码
        System.err.println("orElseGet=" + orElseGet);
        orElseGet = random.ints(100000, 1000000).boxed().map(String::valueOf).findAny().orElseGet(()->"000000"); // 生成6位 String 类型验证码
        System.err.println("orElseGet=" + orElseGet);


        // collect 收集器(收集成 list 集合, set 集合)
        ArrayList<Object> collect = Stream.of(str.split(" ")).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        System.out.println(collect);

        // reduce (对两个输入并返回对应的类型的结果 int applyAsInt(int left, int right) )
        String reduce = Stream.of(str.split(" ")).reduce("aa", String::concat, String::concat);
        System.out.println(reduce);

        Integer sum = Stream.of(str.split(" ")).map(s -> s.length()).reduce(10, (x, y) -> x + y); // 10 + 13 = 23
        System.out.println(sum);
    }
}