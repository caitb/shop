package com.masiis.shop.web.platform.utils;

import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfUserSku;
import com.masiis.shop.dao.po.SfShop;
import com.masiis.shop.web.common.service.UserService;
import com.masiis.shop.web.common.utils.ApplicationContextUtil;
import com.masiis.shop.web.mall.service.shop.SfShopService;
import com.masiis.shop.web.platform.service.user.PfUserCertificateService;
import org.apache.log4j.Logger;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Date 2016/8/26
 * @Author lzh
 */
public class AsyncUploadCertUtil {
    private static Logger log = Logger.getLogger(AsyncUploadCertUtil.class);
    PfUserCertificateService pfUserCertificateService =
            (PfUserCertificateService) ApplicationContextUtil.getBean("pfUserCertificateService");
    UserService userService =
            (UserService) ApplicationContextUtil.getBean("userService");
    SfShopService sfShopService =
            (SfShopService) ApplicationContextUtil.getBean("sfShopService");

    private static class Holder {
        private static final AsyncUploadCertUtil INSTANCE = new AsyncUploadCertUtil();
    }

    private AsyncUploadCertUtil() {
    }

    // 单例懒加载
    public static final AsyncUploadCertUtil getInstance() {
        return Holder.INSTANCE;
    }

    private LinkedBlockingQueue<Object> uploadOSSQueue = null;
    private Thread queueThread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    Object object = uploadOSSQueue.take();
                    if (object instanceof ComUser) {
                        ComUser comUser = (ComUser) object;
                        pfUserCertificateService.asyncUploadUserCertificate(comUser);
                    } else if (object instanceof PfUserSku) {
                        PfUserSku pfUserSku = (PfUserSku) object;
                        ComUser comUser = userService.getUserById(pfUserSku.getUserId());
                        pfUserCertificateService.asyncUploadUserCertificateItem(pfUserSku, comUser);
                    } else if (object instanceof SfShop) {
                        SfShop sfShop = (SfShop) object;
                        sfShopService.createShopPoster(sfShop);
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    });

    {
        uploadOSSQueue = new LinkedBlockingQueue<>();
        queueThread.start();
    }

    public LinkedBlockingQueue<Object> getUploadOSSQueue() {
        return uploadOSSQueue;
    }
}
