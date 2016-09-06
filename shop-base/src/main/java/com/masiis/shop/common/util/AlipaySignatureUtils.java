package com.masiis.shop.common.util;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.internal.util.StringUtils;
import com.alipay.api.internal.util.codec.Base64;

import java.io.ByteArrayInputStream;
import java.security.PublicKey;
import java.security.Signature;

/**
 * @Date 2016/9/6
 * @Author lzh
 */
public class AlipaySignatureUtils extends AlipaySignature {

    public static boolean rsaCheckContent(String content, String sign, String publicKey, String charset) throws AlipayApiException {
        try {
            PublicKey e = getPublicKeyFromX509("RSA", new ByteArrayInputStream(publicKey.getBytes()));
            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initVerify(e);
            if(StringUtils.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            return signature.verify(Base64.decodeBase64(sign.getBytes("UTF-8")));
        } catch (Exception var6) {
            throw new AlipayApiException("RSAcontent = " + content + ",sign=" + sign + ",charset = " + charset, var6);
        }
    }
}
