package com.masiis.shop.web.platform.service.shop;

import com.masiis.shop.common.util.OSSObjectUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 店主的微信二维码　service
 */
@Service
public class SfShopManQrCodeService {

    public String uploadWxQrCodeImg(MultipartFile qrImg) {
        try {
            String suffix = StringUtils.substringAfterLast(qrImg.getOriginalFilename(), ".");
            long size = qrImg.getSize();
            if(size<1 || StringUtils.isEmpty(suffix)) {
                return null;
            }
            String fileName = "wxqrcode_"+ RandomStringUtils.random(20, true, true)+"."+suffix;
            OSSObjectUtils.uploadFile(fileName, size, qrImg.getInputStream(), OSSObjectUtils.OSS_SHOPMAN_WX_QRCODE);
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



}
