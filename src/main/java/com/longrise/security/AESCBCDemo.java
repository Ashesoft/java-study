package com.longrise.security;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class AESCBCDemo {
  private static final String KEY = "1234567890ABCDEF";
  private static final String IV = "longriselongrise";
  public static final String AES_CBC_PKCS7 = "AES/CBC/PKCS7Padding";
  static {
    Security.addProvider(new BouncyCastleProvider());
  }

  public static void main(String[] args) {
    String src = "爱我中华!@#$%^&*()_+<>?::<>?,.//.,";
    try {
      String encryptAES = encryptAES(src);
      System.out.println(encryptAES);
      String decryptAES = decryptAES(encryptAES);
      System.out.println(decryptAES);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static String decryptAES(String src) throws Exception {
    byte[] raw = KEY.getBytes("utf-8");
    SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
    Cipher cipher = Cipher.getInstance(AES_CBC_PKCS7);
    IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes());
    cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParameterSpec);
    byte[] encrypted1 = Hex.decodeHex(src);// 先用base64解密
    byte[] original = cipher.doFinal(encrypted1);
    return new String(original, "utf-8");
  }

  public static String encryptAES(String src) throws Exception {
    byte[] raw = KEY.getBytes("utf-8");
    SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
    Cipher cipher = Cipher.getInstance(AES_CBC_PKCS7);// "算法/模式/补码方式"
    IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes());
    cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec);
    byte[] encrypted = cipher.doFinal(src.getBytes("utf-8"));
    return Hex.encodeHexString(encrypted);
  }
}
