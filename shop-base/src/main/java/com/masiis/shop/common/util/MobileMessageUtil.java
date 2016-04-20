package com.masiis.shop.common.util;

import com.github.pagehelper.StringUtil;
import com.masiis.shop.common.constant.SMSConstants;

import java.util.Random;

/**
 * MobileMessageUtil
 *
 * @author ZhaoLiang
 * @date 2016/3/9
 */
public class MobileMessageUtil {

    private static String sign = "_SMS";

    /**
     * 发送短信验证码
     *
     * @author ZhaoLiang
     * @date 2016/3/9 13:26
     */
//    public static boolean sendIdentifyingCode(String phone) {
//        String code = "";
//        Random random = new Random();
//        for (int i = 0; i < 4; i++) {
//            code += random.nextInt(10);
//        }
//        SpringRedisUtil.saveEx(phone + sign, code, new Integer(SMSConstants.REGESTER_VALID_TIME) * 60 * 1000);
//
//        String[] content = new String[2];
//        content[0] = code;
//        content[1] = SMSConstants.REGESTER_VALID_TIME;
//
//        String[] smsRes = CCPRestSmsSDK.sendSMSWithResult(phone, SMSConstants.REGESTER_TEMPLETE_ID, content);
//        if (!"0".equals(smsRes[0])) {
//            return false;
//        }
//        return true;
//    }
    public static boolean sendMessageForToPay(String phone, String skuName, String agentLevelName) {
        String[] content = new String[2];
        content[0] = skuName;
        content[1] = agentLevelName;
        String[] smsRes = CCPRestSmsSDK.sendSMSWithResult(phone, SMSConstants.TOPAY_TEMPLETE_ID, content);
        if (!"0".equals(smsRes[0])) {
            return false;
        }
        return true;
    }
}
