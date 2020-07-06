package com.longrise.slang;

/**
 * &(按位与) --> 两个为真才为真, 如: 1&1=1, 1&0=0, 0&1=0, 0&0=0
 * 
 * |(按位或) --> 一个为真才为真, 如: 1|0=1, 1|1=1, 0|0=0, 0|1=1
 * 
 * ^(异或运算符) --> 相同为假, 不同为真, 如: 1^0=1, 1^1=0, 0^1=1, 0^0=0
 * 
 */
public class SafeEquals {

    // 类似的方法请看 jdk 中 java.security.MessageDigest 包
    public boolean isEqual(String str1, String str2){
        if(str1.length() != str2.length()){
            return false;
        }
        int equal = 0;
        for (int i = 0; i < str1.length(); i++) {
            // 此处没有及时跳出循环返回, 而是满足循环条件后自动跳出循环并返回
            // 目的是防止 计时攻击 
            equal |= str1.charAt(i) ^ str2.charAt(i);
            System.out.println(equal);
        }
        return equal == 0;
    }
    public static void main(String[] args) {
        SafeEquals safeEquals = new SafeEquals();
        boolean equal = safeEquals.isEqual("AAAAbb", "AAvAvA");
        System.out.println(equal);
    }
}