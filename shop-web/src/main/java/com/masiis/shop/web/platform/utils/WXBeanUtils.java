package com.masiis.shop.web.platform.utils;

import com.alibaba.fastjson.annotation.JSONField;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.KeysUtil;
import com.masiis.shop.common.util.MD5Utils;
import com.masiis.shop.web.platform.beans.pay.wxpay.BrandWCPayReq;
import com.masiis.shop.web.platform.beans.pay.wxpay.Configure;
import com.masiis.shop.web.platform.constants.WxAuthConstants;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by lzh on 2016/3/8.
 */
public class WXBeanUtils {
    private static Logger log = Logger.getLogger(WXBeanUtils.class);

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

    public static void main(String[] args) throws IllegalAccessException {
        BrandWCPayReq br = new BrandWCPayReq();
        br.setAppId(WxAuthConstants.APPID);
        String nonce = "sddfs22dsdf5ssdfa53wq3";
        System.out.println("nonce:" + nonce);
        br.setNonceStr(nonce);
        br.setSignType("MD5");
        br.setTimeStamp("1457513229909");
        br.setPackages("prepay_id=wx2016030916391321529474ea0694275688");
        System.out.println(toSignString(br));
    }

    /**
     * 根据反射来组织bean对象的签名字符串
     *
     * @param obj
     * @return
     */
    public static String toSignString(Object obj) throws IllegalAccessException {
        if(obj == null){
            throw new BusinessException("parameter obj is null");
        }
        Class clazz = obj.getClass();
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> list = new ArrayList<String>();
        for(Field f:clazz.getDeclaredFields()) {
            String key = f.getName();
            f.setAccessible(true);
            JSONField aJF = f.getAnnotation(JSONField.class);
            if (aJF != null) {
                key = aJF.name();
            }
            if("sign".equals(key) || "paySign".equals(key)){
                continue;
            }
            String value = (String) f.get(obj);
            if (StringUtils.isNotBlank(value)) {
                list.add(key + "=" + f.get(obj) + "&");
            }
        }
        int size = list.size();
        String [] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + WxAuthConstants.WX_PAY_SIGN_KEY;
        log.info("Sign Before MD5:" + result);
        result = MD5Utils.encrypt(result).toUpperCase();
        log.info("Sign Result:" + result);
        return result;
    }

}
