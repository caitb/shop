package com.masiis.shop.scheduler.task.platform;

import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.scheduler.platform.service.order.OrderQueueTimeDealService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by wangbingjian on 2016/4/22.
 */
@Component
public class PfQueueTask {

    private static final Logger logger = Logger.getLogger(PfQueueTask.class);
    @Autowired
    private OrderQueueTimeDealService orderQueueTimeDealService;

    @Scheduled(fixedDelay = 1000*60*10)
    public void doSomethingWithDelay(){
        logger.info("定时任务开始执行……开始时间为:" + DateUtil.Date2String(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
        orderQueueTimeDealService.commonQueuingOrder();
        logger.info("定时任务开始执行……结束时间为:" + DateUtil.Date2String(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
    }
}
