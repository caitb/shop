package com.masiis.shop.scheduler.task.platform;

import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.web.platform.service.product.ReplenishStockService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by wangbingjian on 2016/4/26.
 */
@Component
public class ReplenishStockTask {
    private static final Logger logger = Logger.getLogger(ReplenishStockTask.class);

    @Autowired
    private ReplenishStockService replenishStockService;
    /**
     * 补货提醒定时任务
     */
    public void replenishStockRemindJob(){
        logger.info("补货提醒:定时任务开始执行……开始时间为:" + DateUtil.Date2String(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
        try {
            replenishStockService.replenishStockRemind();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("补货提醒:定时任务开始执行……结束时间为:" + DateUtil.Date2String(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
    }

}
