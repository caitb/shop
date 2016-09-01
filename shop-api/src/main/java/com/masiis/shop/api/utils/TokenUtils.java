package com.masiis.shop.api.utils;

import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.common.util.SHAUtils;
import com.masiis.shop.common.util.SysBeanUtils;
import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

/**
 * @Date 2016/4/26
 * @Auther lzh
 */
public class TokenUtils {
    private static long TIME_SALT = 1469772851447l;
    private static String TK_SALT = "!4j0tJjW8^9@tpPn";

    public static String generateToken() {
        String token = null;
        String prToken = null;

        prToken = new Random(System.currentTimeMillis()).nextLong() + "";
        prToken = prToken + (System.currentTimeMillis() + TIME_SALT) + TK_SALT;
        try {
            token = SHAUtils.encodeSHA1(prToken.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return token;
    }

    public static void main(String... args) throws IOException {
        System.out.println(generateToken());
    }
}
