package com.masiis.shop.admin.utils;

import com.alibaba.fastjson.annotation.JSONField;
import com.masiis.shop.common.constant.wx.WxConsSF;
import com.masiis.shop.common.annotation.SignField;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.MD5Utils;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @Date:2016/4/10
 * @auth:lzh
 */
public class WxBeanUtils {
    private static Logger log = Logger.getLogger(WxBeanUtils.class);

    private final static String[] charArrs = {"A", "D", "E", "C", "H", "Y", "6", "7", "8", "9",
            "M", "N", "O", "Z", "1", "5", "P", "Q", "R", "S", "2", "3", "4", "T", "I", "J", "F",
            "G", "B", "K", "L", "W", "X", "U", "V", "0"};

    /**
     * 生成32位随机字符串
     *
     * @return
     */
    public static String createGenerateStr(){
        int len = 32;
        StringBuilder res = new StringBuilder();
        for(int i = 0; i < len; i++){
            res.append(charArrs[(int)(Math.random() * charArrs.length)]);
        }
        return res.toString();
    }

    /**
     * 根据反射来组织bean对象的签名字符串
     *
     * @param obj
     * @return
     */
    public static String toSignString(Object obj) {
        if(obj == null){
            throw new BusinessException("parameter obj is null");
        }
        Class clazz = obj.getClass();
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> list = new ArrayList<String>();
        try {
            for(Field f:clazz.getDeclaredFields()) {
                String key = f.getName();
                f.setAccessible(true);
                JSONField aJF = f.getAnnotation(JSONField.class);
                if (aJF != null) {
                    key = aJF.name();
                }
                SignField sf = f.getAnnotation(SignField.class);
                if(sf != null){
                    continue;
                }
                Object value = f.get(obj);
                if (value != null) {
                    list.add(key + "=" + value + "&");
                }
            }
        } catch (IllegalAccessException e) {
            log.info(e.getMessage());
        }
        int size = list.size();
        String [] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + WxConsSF.API_KEY;
        result = MD5Utils.encrypt(result).toUpperCase();
        return result;
    }
}
