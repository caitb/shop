package com.masiis.shop.web.platform.utils;

import com.github.pagehelper.StringUtil;
import com.masiis.shop.common.constant.SMSConstants;
import com.masiis.shop.common.util.CCPRestSmsSDK;

import java.util.Date;
import java.util.Random;

/**
 * MobileMessageUtil
 *
 * @author ZhaoLiang
 * @date 2016/3/9
 */
public class MobileMessageUtil {

    private String sign = "_SMS";

    /**
     * 发送短信验证码
     *
     * @author ZhaoLiang
     * @date 2016/3/9 13:26
     */
    public boolean sendIdentifyingCode(String phone) {
        String code = "";
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            code += random.nextInt(10);
        }
        SpringRedisUtil.saveEx(phone + sign, code, new Date().getTime() + new Integer(SMSConstants.REGESTER_VALID_TIME) * 60 * 1000);
        String[] content = new String[2];
        content[0] = code;
        content[1] = SMSConstants.REGESTER_VALID_TIME;

        String[] smsRes = CCPRestSmsSDK.sendSMSWithResult(phone, SMSConstants.REGESTER_TEMPLETE_ID, content);
        if (!"0".equals(smsRes[0])) {
            return false;
        }
        return true;
    }

    /**
     * 获取手机验证码
     * @author ZhaoLiang
     * @date 2016/3/9 13:33
     */
    public String getIdentifyingCode(String phone) {
        String code = "";
        if (StringUtil.isNotEmpty(SpringRedisUtil.get(phone + sign, String.class))) {
            code = SpringRedisUtil.get(phone + sign, String.class);
        }
        return code;
    }
}
