package com.masiis.shop.common.util;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import java.util.Date;

/**
 * 活动编码　生成工具
 */
public class PromotionMakeUtils {

    public static  final String promotionStartDateString = "2000-10-10";
    public static  final String promotionStartEndString  = "2010-10-10";
    public static  final Integer giveSkuAgentLevel = 5; //赠送商品的等级
    public static  final Integer giveSkuQuantity = 5;//赠送的商品数量
    public static  final  Integer giveSkuId = 16; //赠送商品的id


    public static String makeCode() {
        String timeStr = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
        String numberRandom = RandomStringUtils.random(4, false, true);
        return "P" + timeStr + numberRandom;
    }



}
