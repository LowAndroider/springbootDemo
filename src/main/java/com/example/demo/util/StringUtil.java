package com.example.demo.util;

public class StringUtil {

    public static String getTokenKey(String str) {
        return "token:" + str;
    }

    public static String value(Object obj) {
        if(obj == null) {
            return null;
        } else {
            return String.valueOf(obj);
        }
    }
}
