package com.longrise.cache;

import com.google.common.cache.CacheBuilder;

public class GuavaCacheDemo {
  public static void main(String[] args) {
    CacheBuilder.newBuilder().concurrencyLevel(Runtime.getRuntime().availableProcessors()).build();
  }
}
