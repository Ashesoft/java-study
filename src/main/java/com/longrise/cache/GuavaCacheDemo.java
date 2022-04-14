package com.longrise.cache;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class GuavaCacheDemo {
  public static void main(String[] args) {
    // CacheBuilder.newBuilder().concurrencyLevel(Runtime.getRuntime().availableProcessors()).build();
    t1();
  }

  private static void t1() {
    Cache<String, String> cache = CacheBuilder.newBuilder().expireAfterAccess(3, TimeUnit.MINUTES).build();
    cache.put("word", "Hello Guava Cache");
    System.out.println("GuavaCacheDemo.t1()");
    System.out.println(cache.getIfPresent("word1"));
  }
}
