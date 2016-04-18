package com.masiis.shop.scheduler.task.mall;

import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.scheduler.mall.business.order.SfOrderTaskService;
import com.masiis.shop.scheduler.mall.business.order.SfUserBillTaskService;
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
    @Resource
    private SfOrderTaskService orderTaskService;

    /**
     * 创建每日结算账单
     */
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

    /**
     * 下单后72小时未支付订单自动取消(目前做成半小时自动扫描取消)
     */
    public void unPaySfOrderCancelJob(){
        Date nowTime = new Date();
        log.info("72小时未支付订单自动取消job开始,开始时间为:" + DateUtil.Date2String(nowTime, "yyyy-MM-dd HH:mm:ss"));
        try{
            // 订单取消
            orderTaskService.autoCancelUnPayOrder();
        } catch (Exception e) {
            log.error("72小时未支付订单自动取消job失败," + e.getMessage());
        }
        log.info("72小时未支付订单自动取消job结束,结束时间为:" + DateUtil.Date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 订单发货后7天自动收货
     */
    public void orderReceivedConfirmJob() {
        Date nowTime = new Date();
        log.info("订单发货7天后自动确认收货job开始,开始时间为:" + DateUtil.Date2String(nowTime, "yyyy-MM-dd HH:mm:ss"));
        try{
            // 订单自动收货
            orderTaskService.confirmOrderReceived();
        } catch (Exception e) {
            log.error("订单发货7天后自动确认收货job失败," + e.getMessage());
        }
        log.info("订单发货7天后自动确认收货job结束,结束时间为:" + DateUtil.Date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
    }
}
