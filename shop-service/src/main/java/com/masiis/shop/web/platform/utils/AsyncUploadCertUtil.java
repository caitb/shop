package com.masiis.shop.web.platform.utils;

import com.masiis.shop.web.common.utils.ApplicationContextUtil;
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

    private static class Holder {
        private static final AsyncUploadCertUtil INSTANCE = new AsyncUploadCertUtil();
    }
    private AsyncUploadCertUtil() {
    }
    // 单例懒加载
    public static final AsyncUploadCertUtil getInstance() {
        return Holder.INSTANCE;
    }

    private LinkedBlockingQueue<Long> uploadOSSQueue = null;
    private Thread queueThread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                Long param = null;
                try {
                    param = uploadOSSQueue.take();
                    pfUserCertificateService.asyncUploadUserCertificate(param);
                } catch (Exception e) {
                    log.error("失败的userId为:" + param);
                    log.error(e.getMessage(), e);
                }
            }
        }
    });
    {
        uploadOSSQueue = new LinkedBlockingQueue<>();
        queueThread.start();
    }

    public LinkedBlockingQueue<Long> getUploadOSSQueue(){
        return uploadOSSQueue;
    }
}
