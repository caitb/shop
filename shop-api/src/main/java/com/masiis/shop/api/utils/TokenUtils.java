package com.masiis.shop.api.utils;

import com.masiis.shop.common.util.DateUtil;

import java.util.Date;
import java.util.UUID;

/**
 * @Date 2016/4/26
 * @Auther lzh
 */
public class TokenUtils {
    public static String generateToken(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static void main(String... args) {
        System.out.println(DateUtil.getDateNextdays(30));
    }
}
