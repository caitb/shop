package com.masiis.shop.common.util;

import java.security.MessageDigest;

/**
 * Created by lzh on 2016/3/10.
 */
public class MD5Utils {

    public static String encrypt(String data) {
        String resultString = null;
        try {
            resultString = new String(data);
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString =parseByte2HexStr(md.digest(resultString.getBytes()));
        } catch (Exception ex) {
        }
        return resultString;
    }

    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    public static void main(String[] args){
        System.out.println(encrypt("appid=wxd5afa1deb29c6197&body=麦士测试商品03&mch_id=1319531601&nonce_str=AAAAAAAAAABBBBBBBBBB223223212&notify_url=http://www.rd.masiis.com&openid=oUIwkwgLzn8CKMDrvbCSE3T-u5fs&out_trade_no=TESTORDER0000000000000003&spbill_create_ip=127.0.0.1&total_fee=1&trade_type=JSAPI&key=U0SJD1OR4WLA5J8QM9IZAJT5KC4ZLS7D"));
    }
}
