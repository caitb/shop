package com.masiis.shop.common.util;

import java.util.regex.Pattern;

/**
 * @Date:2016/4/25
 * @auth:lzh
 */
public class PhoneNumUtils {
    public static boolean isPhoneNum(String input){
        Pattern p = Pattern.compile("^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,6,7,8])|(18[0-9]))[0-9]{8}$");
        return p.matcher(input).matches();
    }

    public static void main(String... args) {
        System.out.println(isPhoneNum("17000009999"));
    }
}
