package com.longrise.stream;

import java.util.stream.IntStream;

// 惰性求值
public class Dxqz {
    public static void main(String[] args) {
        int[] ints = {1,2,3,4};
        
        // 外部迭代
        int sum1 = 0;
        for (int i : ints) {
            sum1 += i * 2;
        }
        System.out.printf("外部迭代的结果为:%d%n", sum1);
        
        // 内部迭代
        // map --> 中间操作(返回流(Stream)的操作)
        // sum --> 终止操作
        int sum2 = IntStream.of(ints).map(Dxqz::doubleNum).sum();
        System.out.printf("内部迭代的结果为:%d%n", sum2);

        System.out.println("惰性求值是指在流没有调用终止操作时, 中间操作是不会执行的");        
        IntStream.of(ints).map(Dxqz::doubleNum);


    }

    public static int doubleNum(int num){
        System.out.println("执行了 doubleNum 方法");
        return num * 2;
    }
}