package com.masiis.shop.admin.utils;

import com.masiis.shop.admin.service.user.ComUserService;
import com.masiis.shop.admin.service.user.PfUserCertificateService;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfUserSku;
import org.apache.log4j.Logger;

import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Date 2016/8/26
 * @Author lzh
 */
public class AsyncUploadCertUtil {
    private static Logger log = Logger.getLogger(AsyncUploadCertUtil.class);
    PfUserCertificateService pfUserCertificateService =
            (PfUserCertificateService) ApplicationContextUtil.getBean("pfUserCertificateService");
    ComUserService comUserService =
            (ComUserService) ApplicationContextUtil.getBean("comUserService");

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
                        ComUser comUser = comUserService.getUserById(pfUserSku.getUserId());
                        pfUserCertificateService.asyncUploadUserCertificateItem(pfUserSku, comUser);
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
