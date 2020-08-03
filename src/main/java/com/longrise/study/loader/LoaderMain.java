package com.longrise.study.loader;

import java.lang.reflect.Method;

public class LoaderMain {
    public static void main(String[] args) {
        t1();
    }

    public static void t1(){
        MyLoader myLoader = new MyLoader(System.getProperty("user.dir") + "/src/main/resources/Hello.class");
        try {
            Class<?> clazz = myLoader.findClass("com.longrise.text.Hello");
            Object obj = clazz.getConstructor().newInstance();
            Method sayHello = clazz.getDeclaredMethod("sayHello");
            sayHello.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}