package com.masiis.shop.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 制作订单工具
 *
 * @author muchaofeng
 * @date $date$ $time$
 */

public class OrderMakeUtils {
    /**
     * 生成订单号
     *
     * @author muchaofeng
     * @date 2016/3/10 15:57
     */

    public static String makeOrder(String code) {
        String[] str = new String[]{"B", "C", "S"};
        SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");
        String date = df.format(new Date());
        String s = "";
        Random random = new Random();
        for (int i = 0; i < 2; i++) {
            s += random.nextInt(10);
        }
        String orderCode = "";
        for (String codeMakes : str) {
            if (code.equals(codeMakes)) {
                orderCode = codeMakes + date + s;
                return orderCode;
            }
        }
        return "";
    }
}