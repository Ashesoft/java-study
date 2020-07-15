package com.longrise.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

// 创建流的方式
public class CreateStream {
    public static void main(String[] args) {
        // list 集合创建流
        List<String> list = new ArrayList<>();
        list.stream(); // 串行流
        list.parallelStream(); // 并行流

        // 数组创建流
        Arrays.stream(new int[] { 1, 2, 3, 4 });

        // 数字创建流
        Random random = new Random();
        random.ints().limit(10);

        Stream.generate(random::ints).limit(20);
    }
}