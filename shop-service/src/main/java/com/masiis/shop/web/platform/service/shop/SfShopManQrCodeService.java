package com.masiis.shop.web.platform.service.shop;

import com.masiis.shop.common.util.OSSObjectUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 店主的微信二维码　service
 */
@Service
public class SfShopManQrCodeService {

    private final static Log log = LogFactory.getLog(SfShopManQrCodeService.class);

    public String uploadWxQrCodeImg(MultipartFile qrImg) {
        try {
            String suffix = StringUtils.substringAfterLast(qrImg.getOriginalFilename(), ".");
            if(StringUtils.isBlank(suffix)) {
                suffix = "png";
            }

            long size = qrImg.getSize();

            log.info("uploadWxQrCodeImg  size:"+size+", suffix="+suffix);

            if(size<1) {
                log.info("uploadWxQrCodeImg 上传的文件不存在。");
                return null;
            }
            String fileName = "wxqrcode_"+ RandomStringUtils.random(20, true, true)+"."+suffix;
            log.info("uploadWxQrCodeImg  fileName : "+fileName);
            OSSObjectUtils.uploadFile(fileName, size, qrImg.getInputStream(), OSSObjectUtils.OSS_SHOPMAN_WX_QRCODE);
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String uploadWxQrCodeImg(FileItem item) {
        try {
            String suffix = StringUtils.substringAfterLast(item.getName(), ".");
            if(StringUtils.isBlank(suffix)) {
                suffix = "png";
            }

            long size = item.getSize();

            log.info("uploadWxQrCodeImg  size:"+size+", suffix="+suffix);

            if(size<1) {
                log.info("uploadWxQrCodeImg 上传的文件不存在。");
                return null;
            }
            String fileName = "wxqrcode_"+ RandomStringUtils.random(20, true, true)+"."+suffix;
            log.info("uploadWxQrCodeImg  fileName : "+fileName);
            OSSObjectUtils.uploadFile(fileName, size, item.getInputStream(), OSSObjectUtils.OSS_SHOPMAN_WX_QRCODE);
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



}
