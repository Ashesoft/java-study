package com.longrise.study.annotation;

/**
 * 使用反射 + JDK 动态代理 + 注解的方式进行程序开发设计
 */
public class Imain {
    public static void main(String[] args) {
        MessageService mService = new MessageService();
        mService.send("msg");
    }
}