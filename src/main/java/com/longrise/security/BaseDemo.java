package com.longrise.security;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

public class BaseDemo {
  private static final String KEY = "1234567890ABCDEF";
  // private static final String IV = "longrise";

  public static void main(String[] args) throws Exception {
    t2();
  }

  public static void t2() throws Exception {
    String str = encryptAES(
        "爱 ♥ 我  中❀华爱 ♥ 我  中❀华爱 ♥ 我  中❀华爱 ♥ 我  中❀华爱 ♥ 我  中❀华爱 ♥ 我  中❀华爱 ♥ 我  中❀华爱 ♥ 我  中❀华爱 ♥ 我  中❀华");
    System.out.println(str);
    String ret = decryptAES(str);
    System.out.println(ret);
  }

  public static void t1() throws UnsupportedEncodingException {
    String str = "深圳市永兴元科技股份有限公司";
    String res = URLEncoder.encode(str, "utf-8").replace("+", "%20");
    String ret = Base64.getEncoder().encodeToString(res.getBytes());
    System.out.println(ret);
  }

  // 加密
  public static String encryptAES(String sSrc) throws Exception {

    byte[] raw = KEY.getBytes("utf-8");
    SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// "算法/模式/补码方式"
    cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
    byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
    return Hex.encodeHexString(encrypted);
    // return Base64.getEncoder().encodeToString(encrypted);//
    // 此处使用BASE64做转码功能，同时能起到2次加密的作用。
  }

  // 解密
  public static String decryptAES(String sSrc) throws Exception {
    try {
      byte[] raw = KEY.getBytes("utf-8");
      SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      cipher.init(Cipher.DECRYPT_MODE, skeySpec);
      // byte[] encrypted1 = Base64.getDecoder().decode(sSrc);// 先用base64解密
      byte[] encrypted1 = Hex.decodeHex(sSrc);// 先用base64解密
      try {
        byte[] original = cipher.doFinal(encrypted1);
        String originalString = new String(original, "utf-8");
        return originalString;
      } catch (Exception e) {
        System.out.println(e.toString());
        return null;
      }
    } catch (Exception ex) {
      System.out.println(ex.toString());
      return null;
    }
  }
}