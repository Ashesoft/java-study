package com.longrise.security;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Objects;

import com.google.common.base.Strings;

public class CustomEncode {
  private static final String I64BIT_TABLE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-~";
  private static final String NORBIT_TABLE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

  private String randomStr(int len) {
    String retStr = "";
    int norLen = NORBIT_TABLE.length();
    for (int i = 0; i < len; i++) {
      retStr += NORBIT_TABLE.charAt((int) (Math.random() * norLen));
    }
    return retStr;
  }

  private String getHashCode(String data) {
    int hash = 1369;
    for (int i = data.length() - 1; i > -1; i--) {
      hash += (hash << 5) + (int) data.charAt(i);
    }

    int val = hash & 0x7FFFFFFF;
    String retVal = "";
    do {
      retVal += I64BIT_TABLE.charAt(val & 0x3F);
      val = (val >> 6);
    } while (val > 0);

    return retVal;
  }

  private String encbase64data(String dataStr) {
    if (Strings.isNullOrEmpty(dataStr)) {
      return null;
    }
    String data = randomStr(5) + dataStr + randomStr(5), hashcode = getHashCode(data);
    int pos1 = (int) Math.floor(data.length() * 0.25), pos2 = (int) Math.floor(data.length() * 0.75);

    hashcode += Strings.repeat("_", 6 - hashcode.length());
    return data.substring(0, pos1) + hashcode + data.substring(pos1, pos2) + randomStr(3) + data.substring(pos2);
  }

  private String decbase64data(String str) {
    if (Strings.isNullOrEmpty(str) || str.length() < 20) {
      return null;
    }

    int len = str.length() - 9,
        pos1 = (int) Math.floor(len * 0.25),
        pos2 = (int) Math.floor(len * 0.75);
    String hashcode = str.substring(pos1, pos1 + 6),
        lastStr = str.substring(0, pos1) + str.substring(pos1 + 6, pos2 + 6) + str.substring(pos2 + 9),
        hcCur = getHashCode(lastStr);
    hcCur += Strings.repeat("_", 6 - hcCur.length());

    if (Objects.equals(hashcode, hcCur)) {
      return lastStr.substring(5, lastStr.length() - 5);
    }
    return "";
  }

  public String encrypt(String data) {
    String from = null;
    try {
      from = Base64.getEncoder().encodeToString(URLEncoder.encode(data, "UTF-8").getBytes());
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return this.encbase64data(from);
  }

  public String decrypt(String data) {
    byte[] to = Base64.getDecoder().decode(this.decbase64data(data));
    try {
      return URLDecoder.decode(new String(to), "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return null;
  }
}
