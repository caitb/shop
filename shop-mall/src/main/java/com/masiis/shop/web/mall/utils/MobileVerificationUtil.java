package com.masiis.shop.web.mall.utils;

import com.github.pagehelper.StringUtil;
import com.masiis.shop.common.constant.SMSConstants;
import com.masiis.shop.common.util.MobileMessageUtil;

import java.util.Random;

/**
 * MobileVerificationUtil
 *
 * @author ZhaoLiang
 * @date 2016/3/9
 */
public class MobileVerificationUtil {

    private static String sign = "_SMS";




    /**
     * 发送短信验证码
     *
     * @author ZhaoLiang
     * @date 2016/3/9 13:26
     */
    public static boolean sendIdentifyingCode(String phone) {
        String code = "";
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            code += random.nextInt(10);
        }
        return MobileMessageUtil.VerificationCode(phone,code,String.valueOf(new Integer(SMSConstants.REGESTER_VALID_TIME)));
        //SpringRedisUtil.saveEx(phone + sign, code, new Integer(SMSConstants.REGESTER_VALID_TIME) * 60 * 1000);
        //return MobileMessageUtil.VerificationCode(phone, code, SMSConstants.REGESTER_VALID_TIME);
    }

    /**
     * 获取手机验证码
     *
     * @author ZhaoLiang
     * @date 2016/3/9 13:33
     */
    public static String getIdentifyingCode(String phone) {
        String code = "";
        if (StringUtil.isNotEmpty(SpringRedisUtil.get(phone + sign, String.class))) {
            code = SpringRedisUtil.get(phone + sign, String.class);
        }
        return code;
    }
}
