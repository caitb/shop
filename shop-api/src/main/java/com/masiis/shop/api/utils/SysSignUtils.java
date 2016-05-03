package com.masiis.shop.api.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.masiis.shop.api.bean.system.GetPhoneValidCodeReq;
import com.masiis.shop.api.bean.system.LoginByPhoneReq;
import com.masiis.shop.api.bean.system.LoginByWxRes;
import com.masiis.shop.api.bean.system.LoginWxReq;
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
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
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

    public static void main(String... args) throws IOException {
        /*LoginWxReq req = new LoginWxReq();
        req.setAppid("sssddsdaajsdkfjdfkjsdfjksskj");
        req.setNickName("测试");
        req.setOpenId("sldkfjIjhsjd_0438skjdhfdskjghgasdkjfh");
        req.setUnionid("sdkjfhd2s7-sldkhHGsldkhjU");
        req.setCity("北京");
        req.setCountry("中国");
        req.setSign(toSignString(req, null));
        String result = HttpClientUtils.httpPost("http://localhost:8083/sys/loginByWx", JSONObject.toJSONString(req));
        System.out.println(result);
        LoginByWxRes res = JSONObject.parseObject(result, LoginByWxRes.class);
        if(StringUtils.isNotBlank(res.getSign()) && res.getSign().equals(toSignString(res, null))){
            System.out.println(true);
        } else {
            System.out.println(false);
        }*/
        /*GetPhoneValidCodeReq req = new GetPhoneValidCodeReq();
        req.setPhoneNum("13671324096");
        req.setSign(toSignString(req, null));
        System.out.println(req);*/

        /*GetPhoneValidCodeReq req = new GetPhoneValidCodeReq();
        req.setPhoneNum("186121");
        req.setSign("skjdfKJHKJH-_LKJSLK");
        String data = JSONObject.toJSONString(req);
        String result = HttpClientUtils.httpPost("http://api.qc.iimai.com/sys/getPhoneValidCode", data);
        System.out.println(result);*/

        /*String aaa = "%7B%22nonceStr%22%3A%22sduJlKLHSksudj_-sklsdkfklds%22%2C%22sign%22%3A%22HDMSKGDFKAHGDKLAJFDKHKAJHSJK%22%2C%22phoneNum%22%3A%2218811346397%22%7D";
        String bbb = "{\"nonceStr\":\"sduJlKLHSksudj_-sklsdkfklds\",\"sign\":\"HDMSKGDFKAHGDKLAJFDKHKAJHSJK\",\"phoneNum\":\"18811346397\"}";
        System.out.println(URLDecoder.decode(bbb, "UTF-8"));*/

        /*ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] aa = {40, 110,117,108,108,41,61,123,34,110,111,110,99,101,83,116,114,34,58,34,115,100,117,74,108};
        os.write(aa);
        System.out.println(new String(os.toByteArray(), "UTF-8"));*/
        LoginByPhoneReq req = new LoginByPhoneReq();
        req.setPhoneNum("13671324096");
        req.setValidcode("6666");
        System.out.println(URLEncoder.encode(JSONObject.toJSONString(req), "UTF-8"));

    }
}
