package com.masiis.shop.api.utils;

import com.masiis.shop.api.constants.SysRedisNameCons;

/**
 * @Date:2016/4/25
 * @auth:lzh
 */
public class ValidCodeUtils {

    private final static String[] charArrs = {"6", "7", "8", "9", "1", "5", "0", "2", "3", "4"};

    public static String generareValidCode(){
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < 4; i++){
            str.append(charArrs[(int)(Math.random() * charArrs.length)]);
        }
        return str.toString();
    }

    public static String getRedisPhoneNumValidCodeName(String phoneNum){
        return phoneNum + SysRedisNameCons.PHONENUM_VALIDCODE_REDIS_NAME_SUFFIX;
    }

    public static String getRdPhoneNumVcodeNextOpTimeName(String phoneNum){
        return phoneNum + SysRedisNameCons.PHONENUM_VALIDCODE_REQUEST_EXPIRE_REDIS_NAME_SUFFIX;
    }
}
