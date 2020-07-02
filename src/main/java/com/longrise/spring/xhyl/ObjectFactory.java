package com.longrise.spring.xhyl;

/**
 * 生产bean的
 * @param <T>
 */
@FunctionalInterface
public interface ObjectFactory<T> {
    T getObject();
}