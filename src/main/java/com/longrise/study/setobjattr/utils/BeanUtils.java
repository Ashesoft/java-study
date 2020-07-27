package com.longrise.study.setobjattr.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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

    // 通过反射创建对象
    public static <T> T createBean(Class<T> clazz, String info) {
        try {
            T object = clazz.getDeclaredConstructor().newInstance();
            setAttrValue(object, info);
            return object;
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
                if(split2[0].contains(".")){ // 属性级联操作
                    String[] splits = split2[0].split("\\.");
                    Object currentObj = obj;
                    for (int i = 0; i < splits.length-1; i++) {
                        Method getMethod = currentObj.getClass().getDeclaredMethod("get" + StringUtils.convertAttrName(splits[i])); // 通过 get 方法获取对象
                        Object tempObj = getMethod.invoke(currentObj); 
                        if(Objects.isNull(tempObj)){ // 判断获取的对象是否为空
                            Field field = currentObj.getClass().getDeclaredField(splits[i]); // 通过反射获取对象指定的成员属性
                            Method setMethod = currentObj.getClass().getDeclaredMethod("set" + StringUtils.convertAttrName(splits[i]), field.getType()); // 获得方法
                            tempObj = field.getType().getDeclaredConstructor().newInstance(); // 通过成员属性类型反射得到属性类型实例化对象
                            setMethod.invoke(currentObj, tempObj);
                        }
                        currentObj = tempObj; // 保存属性对象
                    }
                    Field currentObjField = currentObj.getClass().getDeclaredField(splits[splits.length-1]); // 获取成员属性
                    setObjAttrValue(currentObj, split2[1], currentObjField);
                }else{
                    // 获取指定的对象属性成员
                    Field field = obj.getClass().getDeclaredField(split2[0]); // 获取成员属性
                    setObjAttrValue(obj, split2[1], field);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置属性值
     * @param obj 目标对象
     * @param value 目标对象属性值
     * @param field 目标对象成员属性
     * @throws NoSuchMethodException 没有找到指定的方法
     * @throws IllegalAccessException 参数不合法
     * @throws InvocationTargetException 反射执行失败
     */
    private static void setObjAttrValue(Object obj, String value, Field field) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        // 通过反射获取对象指定的方法
        Method setMethod = obj.getClass().getDeclaredMethod("set" + StringUtils.convertAttrName(field.getName()), field.getType());
        // 转换值的类型 
        Object convertValue = convertAttrValue(field.getType(), value);
        setMethod.invoke(obj, convertValue); // 指定方法的执行
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