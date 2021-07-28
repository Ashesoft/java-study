package com.longrise.study;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.longrise.slambda.Dog;

/**
 * 
 *
 */
public class App {
  public static void main(String[] args) {
    // ConcurrentLinkedQueue<Object> queue = new ConcurrentLinkedQueue<>();
    // queue.add(new Object());
    // Object object = new Object();
    // int loops = 0;
    // Runtime runtime = Runtime.getRuntime();
    // long last = System.currentTimeMillis();
    // while (true) {
    // if(loops % 10000 == 0){
    // long now = System.currentTimeMillis();
    // long duration = now - last;
    // last = now;
    // System.out.println(String.format("duration=%d queue.size=%d memory max=%d
    // free=%d total=%d%n", duration, queue.size(), runtime.maxMemory(),
    // runtime.freeMemory(), runtime.totalMemory()));
    // }
    // queue.add(object);
    // queue.remove(object);
    // ++loops;
    // }
    // createUUID();
    // testArray();
    str();
  }

  public static void testArray() {
    List<Dog> dog1 = new ArrayList<>();
    dog1.add(new Dog("哮天犬1", 30));
    dog1.add(new Dog("哮天犬2", 15));
    dog1.add(new Dog("哮天犬3", 12));
    List<Dog> dog2 = new ArrayList<>();
    dog2.add(new Dog("大黄1"));
    dog2.add(new Dog("大黄2"));
    dog2.add(new Dog("大黄3"));
    List<Dog> dog3 = new ArrayList<>();
    dog3.add(new Dog("小白1"));
    dog3.add(new Dog("小白2"));
    dog3.add(new Dog("小白3"));
    App2 app2 = new App2();
    Map<String, Object> collect = Stream.of(dog1, dog2, dog3).flatMap(List::stream)
        .collect(Collectors.toMap(Dog::getName, map -> app2.getStrFood(map.getFood())));
    List<Dog> collect1 = Stream.of(dog1, dog2, dog3).flatMap(List::stream).collect(Collectors.toList());
    // List<Dog> dogs = new ArrayList<>();
    // dogs.addAll(dog1);
    // dogs.addAll(dog2);
    // dogs.addAll(dog3);
    // System.out.println(dogs);
    System.out.println(collect);
    System.out.println(collect1);
  }

  public static void createUUID() {
    for (int i = 1; i <= 3707; i++) {
      String ss = UUID.randomUUID().toString().replaceAll("-", "");
      System.out.println(ss);
    }
  }

  public static void str() {
    String sql = "曾经有一份真挚的爱情摆在%1$s的面前，但是%1$s没有珍惜，等%1$s失去后才后悔莫及，尘世间最痛苦的事情莫过于此。如果上天能够给%1$s一个再来一次的机会，%1$s会对那个女孩说三个字：%1$s爱你。如果非要在这份爱上加一个期限，%1$s希望是。。。。。。一万年！";
    System.out.println(String.format(sql, "ME"));
  }

}
