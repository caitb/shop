package com.masiis.shop.admin.utils;

import com.masiis.shop.admin.service.user.PfUserCertificateService;
import com.masiis.shop.dao.po.ComUser;
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

    private static class Holder {
        private static final AsyncUploadCertUtil INSTANCE = new AsyncUploadCertUtil();
    }

    private AsyncUploadCertUtil() {
    }

    // 单例懒加载
    public static final AsyncUploadCertUtil getInstance() {
        return Holder.INSTANCE;
    }

    private LinkedBlockingQueue<ComUser> uploadOSSQueue = null;
    private Thread queueThread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                ComUser comUser = null;
                try {
                    comUser = uploadOSSQueue.take();
                    pfUserCertificateService.asyncUploadUserCertificate(comUser);
                } catch (Exception e) {
                    log.error("失败的comUser为:" + comUser.toString());
                    log.error(e.getMessage(), e);
                }
            }
        }
    });

    {
        uploadOSSQueue = new LinkedBlockingQueue<>();
        queueThread.start();
    }

    public LinkedBlockingQueue<ComUser> getUploadOSSQueue() {
        return uploadOSSQueue;
    }
}
