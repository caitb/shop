package com.masiis.shop.scheduler.mall.business.order;

import com.masiis.shop.common.enums.mall.SfOrderStatusEnum;
import com.masiis.shop.common.interfaces.IParallelThread;
import com.masiis.shop.common.util.CurrentThreadUtils;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.dao.po.PfBorder;
import com.masiis.shop.dao.po.SfOrder;
import com.masiis.shop.scheduler.mall.service.order.SfOrderService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Date:2016/4/18
 * @auth:lzh
 */
@Service
public class SfOrderTaskService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private SfOrderService orderService;

    /**
     * 自动取消72小时未支付订单
     */
    public void autoCancelUnPayOrder() {
        Date expiraTime = DateUtil.getDateNextdays(-3);
        log.info("计算过期时间界限点,时间点是:" + DateUtil.Date2String(expiraTime, "yyyy-MM-dd HH:mm:ss"));

        // 查询3天前创建的未支付订单
        // 查询小铺订单
        List<SfOrder> sfList = orderService.findListByStatusAndDate(expiraTime,
                SfOrderStatusEnum.ORDER_UNPAY.getCode(), 0);
        if (sfList == null) {
            log.info("暂无超过72小时未支付小铺订单!");
        } else {
            log.info("超过72小时未支付小铺订单个数:" + sfList.size());
            // 多线程处理
            CurrentThreadUtils.parallelJob(new IParallelThread() {
                @Override
                public Boolean doMyJob(Object obj) throws Exception {
                    SfOrder sfOrder = (SfOrder) obj;
                    try{
                        log.info("开始取消订单,订单号为:" + sfOrder.getOrderCode());
                        orderService.cancelUnPaySfOrder(sfOrder);
                        log.info("取消订单成功,订单号为:" + sfOrder.getOrderCode());
                        return true;
                    } catch (Exception e) {
                        log.info("取消订单失败,订单号为:" + sfOrder.getOrderCode());
                        log.error(e.getMessage(), e);
                    }
                    return false;
                }
            }, new LinkedBlockingDeque<Object>(sfList), 0);
        }

        // 查询分销订单
    }

    /**
     * 订单发货7天后自动收货
     */
    public void confirmOrderReceived() {
        // 7天前时间
        Date expiraTime = DateUtil.getDateNextdays(-7);
        log.info("计算过期时间界限点,时间点是:" + DateUtil.Date2String(expiraTime, "yyyy-MM-dd HH:mm:ss"));

        // 查询已发货状态且发货时间距离现在超过7天
        // 查询代理订单
        List<SfOrder> bList = orderService.findListByStatusAndDate(expiraTime,
                SfOrderStatusEnum.ORDER_SHIPED.getCode(), 1);
        if (bList == null) {
            log.info("暂无超7天未收货代理订单!");
        } else {
            log.info("超过7天未收货代理啊订单个数:" + bList.size());
            // 多线程处理
            for(SfOrder sfOrder:bList) {
                try {
                    log.info("开始代理订单收货,订单号为:" + sfOrder.getOrderCode());
                    orderService.confirmOrderReceive(sfOrder);
                    log.info("代理订单收货成功,订单号为:" + sfOrder.getOrderCode());
                } catch (Exception e) {
                    log.info("代理订单收货失败,订单号为:" + sfOrder.getOrderCode());
                    log.error(e.getMessage(), e);
                }
            }
        }
    }
}
