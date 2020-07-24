package com.longrise.study.setobjattr.utils;

import java.util.Objects;

public class StringUtils{
    private StringUtils(){}
    public static String convertAttrName(String attrName){
        if(Objects.isNull(attrName)){
            return null;
        }
        byte[] bytes = attrName.getBytes();
        bytes[0]-=32;
        return new String(bytes);
    }
}