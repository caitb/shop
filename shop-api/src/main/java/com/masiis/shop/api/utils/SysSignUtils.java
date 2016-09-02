package com.masiis.shop.api.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.masiis.shop.api.bean.system.GetPhoneValidCodeReq;
import com.masiis.shop.api.bean.system.LoginByPhoneReq;
import com.masiis.shop.api.bean.system.LoginByWxRes;
import com.masiis.shop.api.bean.system.LoginWxReq;
import com.masiis.shop.api.bean.user.BankAddReq;
import com.masiis.shop.api.bean.user.PartnerIndexReq;
import com.masiis.shop.common.annotation.SignField;
import com.masiis.shop.common.constant.wx.WxConsPF;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.HttpClientUtils;
import com.masiis.shop.common.util.MD5Utils;
import com.sun.corba.se.spi.orbutil.fsm.Input;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * @Date 2016/4/27
 * @Auther lzh
 */
public class SysSignUtils {
    private static Logger log = Logger.getLogger(SysSignUtils.class);
    private final static String[] charArrs = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};

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
    public static String toSignString(Object obj, String userkey) {
        if(obj == null){
            throw new BusinessException("parameter obj is null");
        }
        Class clazz = obj.getClass();
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> list = new ArrayList<String>();
        try {
            do{
                for(Field f:clazz.getDeclaredFields()) {
                    String key = f.getName();
                    f.setAccessible(true);
                    JSONField aJF = f.getAnnotation(JSONField.class);
                    if (aJF != null) {
                        key = aJF.name();
                    }
                    SignField sf = f.getAnnotation(SignField.class);
                    if (sf != null) {
                        continue;
                    }
                    String value = f.get(obj) == null ? null : f.get(obj).toString();
                    if (StringUtils.isNotBlank(value)) {
                        list.add(key + "=" + f.get(obj) + "&");
                    }
                }
            } while ((clazz = clazz.getSuperclass()) != null);
        } catch (IllegalAccessException e) {
            log.info(e.getMessage());
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        if(StringUtils.isNotBlank(userkey)) {
            result += "key=" + userkey;
        } else {
            if(size > 0){
                result = result.substring(0, result.length() - 1);
            }
        }
        result = MD5Utils.encrypt(result).toUpperCase();
        return result;
    }


    public static String getSignContent(Object obj){
        if(obj == null){
            throw new BusinessException("parameter obj is null");
        }
        Class clazz = obj.getClass();
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> list = new ArrayList<String>();
        try {
            do{
                for(Field f:clazz.getDeclaredFields()) {
                    String key = f.getName();
                    f.setAccessible(true);
                    JSONField aJF = f.getAnnotation(JSONField.class);
                    if (aJF != null) {
                        key = aJF.name();
                    }
                    SignField sf = f.getAnnotation(SignField.class);
                    if (sf != null) {
                        continue;
                    }
                    String value = f.get(obj) == null ? null : f.get(obj).toString();
                    if (StringUtils.isNotBlank(value)) {
                        list.add(key + "=" + f.get(obj) + "&");
                    }
                }
            } while ((clazz = clazz.getSuperclass()) != null);
        } catch (IllegalAccessException e) {
            log.info(e.getMessage());
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result = result.substring(0, result.length() - 1);

        return result;
    }

    public static String getEncodeContent(Object obj){
        if(obj == null){
            throw new BusinessException("parameter obj is null");
        }
        Class clazz = obj.getClass();
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> list = new ArrayList<String>();
        String signName = null;
        String signValue = null;
        try {
            do{
                for(Field f:clazz.getDeclaredFields()) {
                    String key = f.getName();
                    f.setAccessible(true);
                    JSONField aJF = f.getAnnotation(JSONField.class);
                    if (aJF != null) {
                        key = aJF.name();
                    }
                    SignField sf = f.getAnnotation(SignField.class);
                    if (sf != null) {
                        signName = key;
                        signValue = URLEncoder.encode(f.get(obj) + "", "UTF-8");
                        continue;
                    }
                    String value = f.get(obj) == null ? null : f.get(obj).toString();
                    if (StringUtils.isNotBlank(value)) {
                        list.add(key + "=" + URLEncoder.encode(f.get(obj) + "", "UTF-8") + "&");
                    }
                }
            } while ((clazz = clazz.getSuperclass()) != null);
        } catch (IllegalAccessException e) {
            log.info(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
                sb.append(arrayToSort[i]);
        }
        if(StringUtils.isNotBlank(signName) && StringUtils.isNotBlank(signValue)){
            sb.append(signName + "=" + signValue);
        }
        String result = sb.toString();
        result = result.substring(0, result.length() - 1);

        return result;
    }

    public static void main(String... args) throws IOException {
        System.out.println(URLEncoder.encode("=", "UTF-8"));
    }
}
