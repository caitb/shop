package com.masiis.shop.common.util;

import com.vdurmont.emoji.EmojiParser;

/**
 * @Date 2016/5/16
 * @Auther lzh
 */
public class EmojiUtils {
    public static String removeNonBmpUnicode(String str) {
        if (str == null) {
            return null;
        }
        str = EmojiParser.parseToAliases(str);
        return str;
    }

    public static void main(String... args) {
        String str = "An \uD83D\uDE00awesome \uD83D\uDE03string with a few \uD83D\uDE09emojis!";
        String res = removeNonBmpUnicode(str);
        System.out.println(res);
        System.out.println(EmojiParser.parseToUnicode(res));
    }
}
