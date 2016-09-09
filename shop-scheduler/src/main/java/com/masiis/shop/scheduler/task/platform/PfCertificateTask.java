package com.masiis.shop.scheduler.task.platform;

import com.masiis.shop.scheduler.platform.business.user.PfUserCertificateTaskService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Date 2016/9/9
 * @Author lzh
 */
@Component
public class PfCertificateTask {
    private Logger log =  Logger.getLogger(this.getClass());

    @Resource
    private PfUserCertificateTaskService pfUserCertificateTaskService;

    public void autoCreateCertificateJob(){
        pfUserCertificateTaskService.autoCreateCertificate();
    }
}
