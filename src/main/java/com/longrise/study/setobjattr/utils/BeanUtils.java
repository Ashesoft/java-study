package com.longrise.study.setobjattr.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

public class BeanUtils {
    private BeanUtils() {
    }

    public static <T> T initBean1(Class<T> clazz, String info) {
        try {
            T newObj = clazz.getConstructor().newInstance(); // 反射获取指定对象
            setAttrValue(newObj, info);
            return newObj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 为对象设置属性值
    private static void setAttrValue(Object obj, String val){
        String[] split1 = val.split("\\|");
        try {
            for (String string : split1) {
                String[] split2 = string.split("=");
                // 获取指定的对象属性成员
                Field field = obj.getClass().getDeclaredField(split2[0]);
                // 通过反射获取对象指定的方法
                Method setMethod = obj.getClass().getDeclaredMethod("set" + StringUtils.convertAttrName(field.getName()), field.getType());
                // 转换值的类型 
                Object convertValue = convertAttrValue(field.getType(), split2[1]);
                setMethod.invoke(obj, convertValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 将输入的值类型转换成对象属性成员的类型
    private static Object convertAttrValue(Class<?> type, String value){
        if(Objects.equals(type, int.class) || Objects.equals(type, Integer.class)){
            return Integer.parseInt(value);
        }else if(Objects.equals(type, long.class) || Objects.equals(type, Long.class)){
            return Long.parseLong(value);
        }else if(Objects.equals(type, float.class) || Objects.equals(type, Float.class)){
            return Float.parseFloat(value);
        }else if(Objects.equals(type, double.class) || Objects.equals(type, Double.class)){
            return Double.parseDouble(value);
        }else if(Objects.equals(type, Date.class)){
            if(value.matches("\\d{4}-\\d{2}-\\d{2}")){ // 年月日
                return Date.from(LocalDate.parse(value).atStartOfDay().toInstant(ZoneOffset.ofHours(8))); // LocalDate 转 Date1
            }else if(value.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")){ // 年月日 时分秒
                // return LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); // LoackDateTime 转 Date
                return Date.from(LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).atZone(ZoneId.systemDefault()).toInstant()); // LoackDateTime 转 Date
            }
            return new Date();
        }
        return value;
    }
}