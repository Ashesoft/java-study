package com.longrise.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class DemoMain {
  public static void main(String[] args) {
    // t2();
    t4();
  }

  /**
   * map数组根据某些key去重
   */
  public static void t4() {
    Map<String, String> map = null;
    List<Map<String, String>> list = new ArrayList<>();
    map = new HashMap<>();
    map.put("id", "111");
    map.put("A", "x1");
    map.put("B", "y1");
    list.add(map);
    map = new HashMap<>();
    map.put("id", "222");
    map.put("A", "x1");
    map.put("B", "y1");
    list.add(map);
    map = new HashMap<>();
    map.put("id", "333");
    map.put("A", "x3");
    map.put("B", "y3");
    list.add(map);
    map = new HashMap<>();
    map.put("id", "444");
    map.put("A", "x4");
    map.put("B", "y4");
    list.add(map);
    map = new HashMap<>();
    map.put("id", "555");
    map.put("A", "x1");
    map.put("B", "y1");
    list.add(map);

    list = list.stream().filter(distinctByKey(m -> m.get("A").concat(m.get("B")))).collect(Collectors.toList());
    System.out.println(list);
  }

  public static <T> Predicate<T> distinctByKey(Function<? super T, String> keyEctractor) {
    Map<String, Boolean> seen = new ConcurrentHashMap<>();
    return obj -> seen.putIfAbsent(keyEctractor.apply(obj), Boolean.TRUE) == null;
  }

  public static void t3() {
    Map<String, Supplier<List<String>>> action = new HashMap<>();
    action.put("one", DemoMain::one);
    action.put("two", () -> two());
    action.put("three", () -> three());
    action.put("four", () -> four());
    action.put("five", () -> five());
    // Supplier<List<String>> supplier = action.get("aaa");
    // Optional.of(supplier).orElseGet(() -> {
    // () -> Arrays.asList("a", "2", "3");
    // }).get();
    // .(() -> null).get();
    // System.out.println(list);
  }

  public static List<String> one() {
    System.out.println("one");
    return Arrays.asList("a", "2", "3");
  }

  public static List<String> two() {
    System.out.println("two");
    return Arrays.asList("a", "2", "3");
  }

  public static List<String> three() {
    System.out.println("three");
    return Arrays.asList("a", "2", "3");
  }

  public static List<String> four() {
    System.out.println("four");
    return Arrays.asList("a", "2", "3");
  }

  public static List<String> five() {
    System.out.println("five");
    return Arrays.asList("a", "2", "3");
  }

  public static void t2() {
    String s1 = "爱我中华", s2 = "爱我中华是一首歌曲";
    if (s2.contains(s1)) {
      s2.replace(s1, "replacement");
      System.out.println(s2);
    }

  }

  public static void t1() {
    MiDengDemo mDemo = new MiDengDemo();
    String onlyFlag = mDemo.getOnlyFlag();
    Map<String, Object> bean = new HashMap<>();
    bean.put("only_flag", onlyFlag);
    bean.put("data", "data");
    for (int i = 0; i < 3; i++) {
      boolean bool = mDemo.insertData(bean);
      if (bool) {
        System.out.println("sucess");
      } else {
        System.out.println("fail");
      }
    }

    for (int i = 0; i < 3; i++) {
      onlyFlag = mDemo.getOnlyFlag();
      bean = new HashMap<>();
      bean.put("only_flag", onlyFlag);
      bean.put("data", "data");
      boolean bool = mDemo.insertData(bean);
      if (bool) {
        System.out.println("sucess");
      } else {
        System.out.println("fail");
      }
    }
  }
}
