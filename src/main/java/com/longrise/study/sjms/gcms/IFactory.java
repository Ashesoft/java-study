package com.longrise.study.sjms.gcms;

public class IFactory {
    private IFactory() {
    }

    // 使用泛型＋反射
    public static <T> T getInstances(Class<T> clazz) {
        try {
            return clazz.cast(Class.forName(clazz.getName()).getDeclaredConstructor().newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}