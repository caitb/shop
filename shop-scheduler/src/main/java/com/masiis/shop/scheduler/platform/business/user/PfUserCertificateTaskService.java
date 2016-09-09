package com.masiis.shop.scheduler.platform.business.user;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.interfaces.IParallelThread;
import com.masiis.shop.common.util.CurrentThreadUtils;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfUserSku;
import com.masiis.shop.web.common.service.UserService;
import com.masiis.shop.web.platform.service.user.PfUserCertificateService;
import com.masiis.shop.web.platform.service.user.PfUserSkuService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Date 2016/9/9
 * @Author lzh
 */
@Service
public class PfUserCertificateTaskService {
    private Logger log = Logger.getLogger(PfUserCertificateTaskService.class);

    @Resource
    private PfUserSkuService pfUserSkuService;
    @Resource
    private PfUserCertificateService pfUserCertificateService;
    @Resource
    private UserService userService;

    public void autoCreateCertificate() {
        Date time = getNewtimeByMilis(new Date(), -10 * 60 * 1000);
        log.info("检测的时间为:" + DateUtil.Date2String(time, "yyyy-MM-dd HH:mm:ss"));
        List<PfUserSku> pfUserSkuList = pfUserSkuService.getPfUserSkuWithNoCode(time);
        if(pfUserSkuList == null || pfUserSkuList.size() <= 0){
            throw new BusinessException("暂无需要生成证书的数据");
        }
        for(PfUserSku pfUserSku:pfUserSkuList){
            try {
                log.info("创建证书开始,PfUserSkuId:" + pfUserSku.getId());
                // 创建结算日的日账单
                ComUser comUser = userService.getUserById(pfUserSku.getUserId());
                pfUserCertificateService.asyncUploadUserCertificateItem(pfUserSku, comUser);
                log.info("创建证书结束,PfUserSkuId:" + pfUserSku.getId());
            } catch (Exception e) {
                log.error("创建证书失败," + e.getMessage(), e);
            }
        }
    }

    private static Date getNewtimeByMilis(Date date, long milis){
        Calendar ca = Calendar.getInstance();
        ca.setTimeInMillis(date.getTime() + milis);
        return ca.getTime();
    }

    public static void main(String... args) {
        System.out.println(getNewtimeByMilis(new Date(), -10 * 60 * 1000));
    }
}
