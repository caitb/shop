package com.masiis.shop.scheduler.task.platform;

import com.masiis.shop.scheduler.platform.business.user.PfUserShopService;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Date 2016/9/28
 * @Author lzh
 */
@Component
public class PfUserTask {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private PfUserShopService pfUserShopService;

    @Scheduled(cron = "0 15 * * * ?")
    public void autoCloseUnAuditUserShopJob(){
        log.info("自动关闭未实名认证代理商店铺定时任务开始");
        try{
            pfUserShopService.autoCloseUnAuditUserShop();
        } catch (Exception e) {
            log.error("自动关闭未实名代理商店铺失败");
        }
        log.info("自动关闭未实名认证代理商店铺定时任务结束");
    }
}
