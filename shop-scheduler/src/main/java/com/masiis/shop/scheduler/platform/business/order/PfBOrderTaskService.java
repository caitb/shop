package com.masiis.shop.scheduler.platform.business.order;

import com.masiis.shop.common.enums.platform.BOrderStatus;
import com.masiis.shop.common.interfaces.IParallelThread;
import com.masiis.shop.common.util.CurrentThreadUtils;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.dao.po.PfBorder;
import com.masiis.shop.scheduler.platform.service.order.BOrderShipService;
import com.masiis.shop.scheduler.platform.service.order.PfBorderService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by lzh on 2016/3/30.
 */
@Service
public class PfBOrderTaskService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private PfBorderService bOrderService;
    @Resource
    private BOrderShipService bOrderShipService;

    /**
     * 自动取消7天未支付的线下支付订单
     */
    public void autoCancelOfflineBorder(){
        Date expiraTime = DateUtil.getDateNextdays(-7);
        log.info("计算过期时间界限点,时间点是:" + DateUtil.Date2String(expiraTime, "yyyy-MM-dd HH:mm:ss"));

        // 查询3天前创建的未支付非升级订单
        // 查询代理订单
        List<PfBorder> bList = bOrderService.findUnUpgradeByStatusAndDate(expiraTime,
                BOrderStatus.offLineNoPay.getCode(), 0);
        if (bList == null) {
            log.info("暂无超过7天未支付线下支付代理订单!");
        }
        log.info("超过7天未支付线下支付代理订单个数:" + bList.size());
        final List<PfBorder> notices = new ArrayList<>();
        // 多线程处理
        CurrentThreadUtils.parallelJob(new IParallelThread() {
            @Override
            public Boolean doMyJob(Object obj) throws Exception {
                PfBorder bOrder = (PfBorder) obj;
                try{
                    log.info("开始取消线下订单,订单号为:" + bOrder.getOrderCode());
                    bOrderService.cancelOfflinePayBOrder(bOrder);
                    log.info("取消订单线下成功,订单号为:" + bOrder.getOrderCode());
                    synchronized (this){
                        notices.add(bOrder);
                    }
                    return true;
                } catch (Exception e) {
                    log.info("取消线下订单失败,订单号为:" + bOrder.getOrderCode());
                    log.error(e.getMessage(), e);
                }
                return false;
            }
        }, new LinkedBlockingDeque<Object>(bList), 0);

        for(PfBorder np:notices){
            bOrderService.sendWxNoitceByCancelBorder(np, 7);
        }
    }

    /**
     * 自动取消72小时未支付订单
     */
    public void autoCancelUnPayOrder() {
        Date expiraTime = DateUtil.getDateNextdays(-3);
        log.info("计算过期时间界限点,时间点是:" + DateUtil.Date2String(expiraTime, "yyyy-MM-dd HH:mm:ss"));

        // 查询3天前创建的未支付非升级订单
        // 查询代理订单
        List<PfBorder> bList = bOrderService.findUnUpgradeByStatusAndDate(expiraTime, 0, 0);
        if (bList == null) {
            log.info("暂无超过72小时未支付代理订单!");
        }
        log.info("超过72小时未支付代理订单个数:" + bList.size());
        final List<PfBorder> notices = new ArrayList<>();
        // 多线程处理
        CurrentThreadUtils.parallelJob(new IParallelThread() {
            @Override
            public Boolean doMyJob(Object obj) throws Exception {
                PfBorder bOrder = (PfBorder) obj;
                try{
                    log.info("开始取消订单,订单号为:" + bOrder.getOrderCode());
                    bOrderService.cancelUnPayBOrder(bOrder);
                    log.info("取消订单成功,订单号为:" + bOrder.getOrderCode());
                    synchronized (this){
                        notices.add(bOrder);
                    }
                    return true;
                } catch (Exception e) {
                    log.info("取消订单失败,订单号为:" + bOrder.getOrderCode());
                    log.error(e.getMessage(), e);
                }
                return false;
            }
        }, new LinkedBlockingDeque<Object>(bList), 0);

        for(PfBorder np:notices){
            bOrderService.sendWxNoitceByCancelBorder(np, 3);
        }
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
        List<PfBorder> bList = bOrderService.findListByStatusAndDate(expiraTime,
                BOrderStatus.Ship.getCode(), 1);
        if (bList == null) {
            log.info("暂无超7天未收货代理订单!");
        } else {
            log.info("超过7天未收货代理订单个数:" + bList.size());
            // 多线程处理
            for(PfBorder bOrder:bList) {
                try {
                    log.info("开始代理订单收货,订单号为:" + bOrder.getOrderCode());
                    bOrderShipService.shipAndReceiptBOrder(bOrder);
                    log.info("代理订单收货成功,订单号为:" + bOrder.getOrderCode());
                } catch (Exception e) {
                    log.info("代理订单收货失败,订单号为:" + bOrder.getOrderCode());
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.getDateNextdays(-7));
    }

}
