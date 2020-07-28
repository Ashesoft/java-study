package com.longrise.study.jh;

import java.util.HashSet;
import java.util.Set;

/**
 * Set 集合的使用
 * 
 * HashSet 使用, 它通过 Object 类提供的 hashCode() 和 equals(Object o) 方法来判断是否是重复元素, 所以要重写 hashCode() 和 equals(Object o) 方法
 * TreeSet 使用, 它通过实现 Comparable 接口来以此判断是否为重复元素.(有序)
 */
public class HashSetMain {
    public static void main(String[] args) {
        Set<User> users = new HashSet<>();
        users.add(new User("张三", 12));
        users.add(new User("李四", 12));
        users.add(new User("王五", 12));
        users.add(new User("陈六", 12));
        users.add(new User("田七", 12));
        users.add(new User("王五", 12));
        users.forEach(System.out::println);
    }
}