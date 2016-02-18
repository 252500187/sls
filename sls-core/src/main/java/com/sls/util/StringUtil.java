package com.sls.util;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class StringUtil {

    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-","");
    }

    public static String iso2utf(String str) {
        try {
            return new String(str.getBytes("iso-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String iso2gbk(String str) {
        try {
            return new String(str.getBytes("iso-8859-1"), "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
