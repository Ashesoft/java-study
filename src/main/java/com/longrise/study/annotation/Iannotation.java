package com.longrise.study.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 自定义一个注解
@Retention(RetentionPolicy.RUNTIME) // 注解生效策略
@Target(ElementType.TYPE) // 注解作用的目标位置
public @interface Iannotation {
    Class<?> value();
}