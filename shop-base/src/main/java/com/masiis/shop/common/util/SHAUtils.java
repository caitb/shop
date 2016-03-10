package com.masiis.shop.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by lzh on 2016/3/9.
 */
public class SHAUtils {
    /*
     * 注：
     * 1、安全哈希算法（Secure Hash Algorithm）主要适用于数字签名标准 （Digital Signature Standard DSS）里面定义的
     * 数字签名算法（Digital Signature Algorithm DSA）。对于长度小于2^64位的消息，SHA1会产生一个160位的消息摘要。
     * 2、SHA-1与MD5的比较：前者在长度上长32 位；强行攻击有更大的强度
     * 3、MD5输出128bit、SHA1输出160bit、SHA256输出256bit、另外还有SHA244,SHA512，分别输出244bit，512bit
     */

    public static final String ALGORITHM = "SHA1";

    public static String encodeSHA1(byte[] content){
        try{
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            digest.update(content);
            return parseByte2HexStr(digest.digest());
        }catch(NoSuchAlgorithmException e){

        }
        return null;
    }

    /**将二进制转换成16进制
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String str = "jsapi_ticket=kgt8ON7yVITDhtdwci0qebK2eRD5_uz9mQq_BVZhOirRoH2P7RAkTNZ7ya2-MuaRi4CoWN6-xRGY-wNdYeFUYA&noncestr=sddfs22dsdf5ssdfa53wq3&timestamp=1457513229909&url=http://www.rd.masiis.com/wxpay/wtpay";
        System.out.println(encodeSHA1(str.getBytes()));
    }

}
