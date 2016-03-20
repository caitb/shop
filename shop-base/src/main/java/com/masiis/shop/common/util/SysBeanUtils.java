package com.masiis.shop.common.util;

import com.masiis.shop.common.exceptions.BusinessException;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.regex.Pattern;

/**
 *
 *
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
    public static boolean isAllPropertiesBlank(Object obj){
        if(obj == null){
            throw new BusinessException("param is null");
        }
        Class clazz = obj.getClass();
        Method[] ms = clazz.getMethods();
        for(Method m : ms){
            String name = m.getName();
            if (name.startsWith("get") && m.getParameterTypes().length == 0) {
                try {
                    Object par = m.invoke(obj);
                    if(par == null){
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

    public static String createPaySerialNumByOrderType(String orderType){
        if(StringUtils.isBlank(orderType)){
            throw new BusinessException("orderType is null");
        }
        if(orderType.length() != 1){
            throw new BusinessException("orderType is invalid");
        }
        StringBuilder res = new StringBuilder(orderType);
        // 流水号的次级开头字符串
        res.append("LSH");
        res.append(DateUtil.Date2String(new Date(), "yyyyMMddHHmmssSSS"));
        for(int i = 0; i < 11; i++){
            res.append(charArrs[(int)(Math.random() * charArrs.length)]);
        }
        return res.toString();
    }

    /**
     * 判断字符串能否转数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        String[] strArr = str.split(".");
        if(strArr.length > 2){
            return false;
        }
        if(strArr.length == 1){
            return Pattern.compile("[0-9]*").matcher(str).matches();
        }
        return isNumeric(strArr[0]) && isNumeric(strArr[1]);
    }

    public static void main(String[] aa) {
        System.out.println(isNumeric("23.45"));
    }
}
