package com.longrise.stream;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

// 收集器
public class CollectDemo {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student("小红", 5, '男', "two"));
        students.add(new Student("小橙", 6, '女', "three"));
        students.add(new Student("小黄", 5, '男', "two"));
        students.add(new Student("小绿", 7, '女', "three"));
        students.add(new Student("小青", 8, '男', "four"));
        students.add(new Student("小蓝", 9, '女', "four"));
        students.add(new Student("小紫", 4, '男', "two"));
        students.add(new Student("阿红", 6, '女', "three"));
        students.add(new Student("阿紫", 8, '男', "four"));
        students.add(new Student("阿梅", 9, '女', "four"));
        students.add(new Student("阿嚏", 7, '男', "three"));
        students.add(new Student("阿偶", 5, '女', "two"));

        // 获取所有学生的年龄列表
        ArrayList<Object> ageList = students.stream().mapToInt(Student::getAge).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        System.out.printf("年龄 List 集合:%s%n", ageList);
        List<Integer> collectList = students.stream().map(Student::getAge).collect(Collectors.toList());
        System.out.printf("年龄 List 集合:%s%n", collectList);
        HashSet<Object> ageSet = students.stream().mapToInt(Student::getAge).collect(HashSet::new, HashSet::add, HashSet::addAll);
        System.out.printf("年龄 Set 集合:%s%n", ageSet);
        TreeSet<Integer> collectSet = students.stream().map(Student::getAge).collect(Collectors.toCollection(TreeSet::new));
        System.out.printf("年龄 Set 集合:%s%n", collectSet);

        // 统计汇总信息
        IntSummaryStatistics summary = students.stream().collect(Collectors.summarizingInt(Student::getAge));
        System.out.printf("学生年龄汇总:%s%n", summary);

        // 分块(分组的特殊形式)
        Map<Boolean, List<Student>> map = students.stream().collect(Collectors.partitioningBy(s -> s.getSex() == '男'));
        System.out.printf("学生男女列表:%s%n", map);

        // 分组
        Map<String, List<Student>> collect = students.stream().collect(Collectors.groupingBy(Student::getGrade));
        System.out.printf("各年级学生列表:%s%n", collect);
        Map<String, Long> collect2 = students.stream().collect(Collectors.groupingBy(Student::getGrade, Collectors.counting()));
        System.out.printf("各年级学生人数列表:%s%n", collect2);
        ConcurrentHashMap<String, Long> collect3 = students.stream().collect(Collectors.groupingBy(Student::getGrade, ConcurrentHashMap::new, Collectors.counting()));
        System.out.printf("各年级学生人数列表:%s%n", collect3);
    }
    
}