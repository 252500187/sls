package com.sls.util;

import java.io.UnsupportedEncodingException;

public class BaseUtil {

    public static String iso2utf(String str){
        try {
            return new String(str.getBytes("iso-8859-1"),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
