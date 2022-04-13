package com.longrise.security;

public class ConvertToolTest {
  public static void main(String[] args) {
    // String t2 = t2("a-😊-⁇-₭-ɶ-∳-⨸-㈢-⺤-⺠-⻱-|");
    String t2 = t2("测试007");
    System.out.println(t2);
    String t3 = t3(t2);
    System.out.println(t3);
  }

  // 解码
  public static String t3(String src) {
    String[] strs = src.split("-");
    int code, len = strs.length;

    StringBuilder sBuilder = new StringBuilder();
    for (int i = 0; i < len; i++) {
      code = Integer.parseInt(strs[i], 16);
      sBuilder.append((char) code);
    }
    return sBuilder.toString();
  }

  // 编码
  public static String t2(String src) {
    String hexStr;
    int len = src.length();
    String[] hexs = new String[len];
    for (int i = 0; i < len; i++) {
      hexStr = Integer.toHexString((int) src.charAt(i));
      hexs[i] = hexStr;
    }
    return String.join("-", hexs);
  }

  public static void t1() {
    String s1 = ConvertTool.bytesToHexString("中华".getBytes());
    System.out.println(s1);
    byte[] b1 = ConvertTool.hexStringToByte(s1);
    System.out.println(new String(b1));
  }
}
