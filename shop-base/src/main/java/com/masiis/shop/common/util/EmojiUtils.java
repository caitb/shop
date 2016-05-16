package com.masiis.shop.common.util;

/**
 * @Date 2016/5/16
 * @Auther lzh
 */
public class EmojiUtils {
    public static String removeNonBmpUnicode(String str) {
        if (str == null) {
            return null;
        }
        str = str.replaceAll("[^\\u0000-\\uFFFF]", "");
        return str;
    }
}
