package com.masiis.shop.scheduler.task.mall;

import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.scheduler.mall.business.SfUserBillTaskService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Date:2016/4/14
 * @auth:lzh
 */
@Component
public class SfOrderTask {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private SfUserBillTaskService billTaskService;

    public void sfBillCountJob() {
        log.info("创建每日小铺结算账单定时任务开始,开始时间为:" + DateUtil.Date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
        try{
            billTaskService.createSfUserBillByDaily();
            log.error("创建每日小铺结算账单定时任务成功");
        } catch (Exception e) {
            log.error("创建每日小铺结算账单定时任务错误,{}" + e.getMessage());
        }
        log.info("创建每日小铺结算账单定时任务结束,结束时间为:" + DateUtil.Date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
    }
}
