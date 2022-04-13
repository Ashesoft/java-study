package com.longrise.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.apache.commons.codec.binary.Base64;

public class CustomEncodeTest {
  public static void main(String[] args) {
    t2();
  }

  public static void t2() {
    try {
      KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
      KeyPair kp = kpg.genKeyPair();
      PublicKey publicKey = kp.getPublic();
      PrivateKey privateKey = kp.getPrivate();
      String pubKey = Base64.encodeBase64String(publicKey.getEncoded());
      String priKey = Base64.encodeBase64String(privateKey.getEncoded());
      System.out.println("pubKey==>" + pubKey);
      System.out.println("priKey==>" + priKey);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
  }

  public static void t1() {
    String ss = "中华";
    CustomEncode sdemo = new CustomEncode();
    String from = sdemo.encrypt(ss);
    System.out.println(from);
    String to = sdemo.decrypt(from);
    System.out.println(to);
  }
}