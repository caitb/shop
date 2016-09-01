package com.masiis.shop.common.util;

import com.masiis.shop.common.exceptions.BusinessException;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Created by lzh on 2016/3/15.
 */
public class SysBeanUtils {
    private static final String[] charArrs = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};

    /**
     * 检测对象中的属性是否都为null
     *
     * @param obj
     * @return
     */
    public static boolean isAllPropertiesBlank(Object obj) {
        if (obj == null) {
            throw new BusinessException("param is null");
        }
        Class clazz = obj.getClass();
        Method[] ms = clazz.getMethods();
        for (Method m : ms) {
            String name = m.getName();
            if (name.startsWith("get") && m.getParameterTypes().length == 0) {
                try {
                    Object par = m.invoke(obj);
                    if (par == null) {
                        return false;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        return true;
    }

    public static String createPaySerialNumByOrderType(String orderType) {
        if (StringUtils.isBlank(orderType)) {
            throw new BusinessException("orderType is null");
        }
        if (orderType.length() != 1) {
            throw new BusinessException("orderType is invalid");
        }
        StringBuilder res = new StringBuilder(orderType);
        // 流水号的次级开头字符串
        res.append("LSH");
        res.append(DateUtil.Date2String(new Date(), "yyyyMMddHHmmssSSS"));
        for (int i = 0; i < 11; i++) {
            res.append(charArrs[(int) (Math.random() * charArrs.length)]);
        }
        return res.toString();
    }

    /**
     * 判断字符串能否转数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        if ((str.lastIndexOf("-") == 0 && str.lastIndexOf("+") < 0)
                || (str.lastIndexOf("-") <= 0 && str.lastIndexOf("+") == 0)) {
            return isNumeric(str.substring(1, str.length()));
        }
        if (str.charAt(0) == '.') {
            return false;
        }
        if (str.charAt(str.length() - 1) == '.') {
            return false;
        }
        String[] strArr = str.split("\\.");
        if (strArr.length > 2) {
            return false;
        }
        if (strArr.length == 1) {
            return Pattern.compile("[0-9]*").matcher(str).matches();
        }
        return isNumeric(strArr[0]) && isNumeric(strArr[1]);
    }

    /**
     * 根据状态位来创建不同的账户操作流水号
     *
     * @param type
     * @return
     */
    public static String createAccountRecordSerialNum(int type) {
        StringBuilder res = new StringBuilder("LSHM");
        res.append(DateUtil.Date2String(new Date(), "yyyyMMddHHmmssSSS"));
        for (int i = 0; i < 11; i++) {
            res.append(charArrs[(int) (Math.random() * charArrs.length)]);
        }
        return res.toString();
    }

    /**
     * 创建小铺用户提现付款流水号
     *
     * @return
     */
    public static String createSfUserExtractPaySerialNum() {
        StringBuilder res = new StringBuilder("LSHN");
        res.append(DateUtil.Date2String(new Date(), "yyyyMMddHHmmssSSS"));
        for (int i = 0; i < 11; i++) {
            res.append(charArrs[(int) (Math.random() * charArrs.length)]);
        }
        return res.toString();
    }

    public static void main(String[] aa) {
        System.out.println(createAccountRecordSerialNum(0));
    }

    public static String createSfAccountRecordSerialNum() {
        StringBuilder res = new StringBuilder("LSHH");
        res.append(DateUtil.Date2String(new Date(), "yyyyMMddHHmmssSSS"));
        for (int i = 0; i < 11; i++) {
            res.append(charArrs[(int) (Math.random() * charArrs.length)]);
        }
        return res.toString();
    }

    public static String createStorageBillCode() {
        StringBuilder res = new StringBuilder("SC");
        res.append(DateUtil.Date2String(new Date(), "yyyyMMddHHmmssSSS"));
        for (int i = 0; i < 7; i++) {
            res.append(charArrs[(int) (Math.random() * charArrs.length)]);
        }
        return res.toString();
    }

    /**
     * 生成随机文件名：当前年月日时分秒+五位随机数
     *
     * @return
     */
    public static String getRandomFileName() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String str = simpleDateFormat.format(date);
        Random random = new Random();
        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
        return rannum + str;// 当前时间
    }
}
