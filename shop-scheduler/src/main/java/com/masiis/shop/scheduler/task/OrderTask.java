package com.masiis.shop.scheduler.task;

import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.dao.platform.order.PfBorderMapper;
import com.masiis.shop.dao.po.PfBorder;
import com.masiis.shop.scheduler.business.order.PfUserBillTaskService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by lzh on 2016/3/23.
 */
@Component
public class OrderTask {
    private Logger log =  Logger.getLogger(this.getClass());

    @Resource
    private PfUserBillTaskService billService;
    /**
     * 创建每日结算账单
     */
    public void billCountJob(){
        log.info("创建每日结算账单定时任务开始,开始时间为:" + DateUtil.Date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
        try{
            billService.createPfUserBillByDaily();
        } catch (Exception e) {
            log.error("创建每日结算账单定时任务错误,{}" + e.getMessage());
        }
        log.info("创建每日结算账单定时任务结束,结束时间为:" + DateUtil.Date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
    }


    /**
     * 下单后72小时未支付订单自动取消
     */
    public void unPayOrderCancelJob(){

    }
}
