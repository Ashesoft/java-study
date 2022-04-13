package com.longrise.security;

import java.util.Optional;

import com.google.common.base.Strings;

public class ConvertToolTest {
  public static void main(String[] args) {
    String str = "😊";
    System.out.println(str.charAt(0));
    System.out.println((int) str.charAt(0));
    System.out.println((int) str.codePointAt(0));

    String t2 = t2("a-😊-⁇-₭-ɶ-∳-⨸-㈢-⺤-⺠-⻱-|");
    // String t2 = t2("测试007");
    System.out.println(t2);
    // 61-2d-1f60a-de0a-2d-2047-2d-20ad-2d-276-2d-2233-2d-2a38-2d-3222-2d-2ea4-2d-2ea0-2d-2ef1-2d-7c
    String t3 = t3(t2);
    System.out.println(t3);
  }

  // 解码
  public static String t3(String src) {
    return Optional.of(src)
        .filter(Strings::isNullOrEmpty)
        .orElseGet(() -> {
          String[] hexary = src.split("-");
          StringBuilder stringBuilder = new StringBuilder();
          for (int i = 0, len = hexary.length; i < len; i++) {
            stringBuilder.append((char) (Integer.parseInt(hexary[i], 16)));
          }
          return stringBuilder.toString();
        });
  }

  // 编码
  public static String t2(String src) {
    return Optional.of(src)
        .filter(Strings::isNullOrEmpty)
        .orElseGet(() -> {
          int len = src.length();
          String[] hexary = new String[len];
          for (int i = 0; i < len; i++) {
            hexary[i] = Integer.toHexString(src.charAt(i));
          }
          return String.join("-", hexary);
        });
  }

  public static void t1() {
    String s1 = ConvertTool.bytesToHexString("中华".getBytes());
    System.out.println(s1);
    byte[] b1 = ConvertTool.hexStringToByte(s1);
    System.out.println(new String(b1));
  }
}
