package com.masiis.shop.common.util;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import java.util.Date;

/**
 * 活动编码　生成工具
 */
public class PromotionMakeUtils {

    public static String makeCode() {
        String timeStr = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
        String numberRandom = RandomStringUtils.random(4, false, true);
        return "P" + timeStr + numberRandom;
    }

}
