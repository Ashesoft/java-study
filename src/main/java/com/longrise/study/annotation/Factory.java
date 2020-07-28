package com.longrise.study.annotation;

/**
 * 封装各个类型的消息, 使它们有一个统一的访问入口, 并且不拘于任何类型
 */
public class Factory {
    private Factory() {
    }

    @SuppressWarnings("unchecked")
    public static <T> T getProxyInstance(Class<T> clazz) {
        try {
            return (T) new MessageProxy().bind(clazz.getDeclaredConstructor().newInstance());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}