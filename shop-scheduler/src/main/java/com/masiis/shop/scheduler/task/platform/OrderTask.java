package com.masiis.shop.scheduler.task.platform;

import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.dao.platform.order.PfBorderMapper;
import com.masiis.shop.dao.po.PfBorder;
import com.masiis.shop.scheduler.platform.business.order.PfBOrderTaskService;
import com.masiis.shop.scheduler.platform.business.order.PfUserBillTaskService;
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
    private PfUserBillTaskService billTaskService;
    @Resource
    private PfBOrderTaskService bOrderTaskService;

    /**
     * 创建每日结算账单
     */
    public void billCountJob(){
        log.info("创建每日结算账单定时任务开始,开始时间为:" + DateUtil.Date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
        try{
            billTaskService.createPfUserBillByDaily();
            log.error("创建每日结算账单定时任务成功");
        } catch (Exception e) {
            log.error("创建每日结算账单定时任务错误,{}" + e.getMessage());
        }
        log.info("创建每日结算账单定时任务结束,结束时间为:" + DateUtil.Date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
    }


    /**
     * 下单后72小时未支付订单自动取消(目前做成半小时自动扫描取消)
     */
    public void unPayOrderCancelJob(){
        Date nowTime = new Date();
        log.info("72小时未支付订单自动取消job开始,开始时间为:" + DateUtil.Date2String(nowTime, "yyyy-MM-dd HH:mm:ss"));
        try{
            // 订单取消
            bOrderTaskService.autoCancelUnPayOrder();
        } catch (Exception e) {
            log.error("72小时未支付订单自动取消job失败," + e.getMessage());
        }
        log.info("72小时未支付订单自动取消job结束,结束时间为:" + DateUtil.Date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
    }


    /**
     * 订单发货后7天自动收货
     */
    public void orderReceivedConfirmJob(){
        Date nowTime = new Date();
        log.info("订单发货7天后自动确认收货job开始,开始时间为:" + DateUtil.Date2String(nowTime, "yyyy-MM-dd HH:mm:ss"));
        try{
            // 订单自动收货
            bOrderTaskService.confirmOrderReceived();
        } catch (Exception e) {
            log.error("订单发货7天后自动确认收货job失败," + e.getMessage());
        }
        log.info("订单发货7天后自动确认收货job结束,结束时间为:" + DateUtil.Date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
    }
}
