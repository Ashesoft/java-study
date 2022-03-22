package com.longrise.security;

public class CustomEncodeTest {
  public static void main(String[] args) {
    String ss = "中华";
    CustomEncode sdemo = new CustomEncode();
    String from = sdemo.encrypt(ss);
    System.out.println(from);
    String to = sdemo.decrypt(from);
    System.out.println(to);
  }
}