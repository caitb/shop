package com.masiis.shop.common.util;

import com.vdurmont.emoji.EmojiParser;

/**
 * @Date 2016/5/16
 * @Auther lzh
 */
public class EmojiUtils {
    public static String encodeEmojiStr(String str) {
        if (str == null) {
            return null;
        }
        str = EmojiParser.parseToAliases(str);
        return str;
    }

    public static String parseEmojiToUnicode(String str){
        if (str == null) {
            return null;
        }
        return EmojiParser.parseToUnicode(str);
    }

    public static void main(String... args) {
        String str = "An \uD83D\uDE00awesome \uD83D\uDE03string with a few \uD83D\uDE09emojis!";
        String res = encodeEmojiStr(str);
        System.out.println(res);
        System.out.println(EmojiParser.parseToUnicode(res));
    }
}
