package com.masiis.shop.scheduler.mall.service.order;

import com.masiis.shop.common.enums.BOrder.BOrderStatus;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.dao.mall.order.SfOrderMapper;
import com.masiis.shop.dao.mall.order.SfOrderOperationLogMapper;
import com.masiis.shop.dao.mall.user.SfUserAccountMapper;
import com.masiis.shop.dao.po.PfBorder;
import com.masiis.shop.dao.po.PfBorderOperationLog;
import com.masiis.shop.dao.po.SfOrder;
import com.masiis.shop.dao.po.SfOrderOperationLog;
import com.masiis.shop.scheduler.mall.service.user.SfUserAccountService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Date:2016/4/14
 * @auth:lzh
 */
@Service
public class SfOrderService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private SfOrderMapper sfOrderMapper;
    @Resource
    private SfOrderOperationLogMapper logMapper;
    @Resource
    private SfUserAccountMapper sfAccountMapper;
    @Resource
    private SfUserAccountService sfUserAccountService;

    /**
     * 查询指定过期时间的待取消订单
     *
     * @param expiraTime
     * @param orderStatus
     * @param payStatus
     * @return
     */
    public List<SfOrder> findListByStatusAndDate(Date expiraTime, int orderStatus, int payStatus) {
        log.info("查询创建时间大于:" + DateUtil.Date2String(expiraTime, "yyyy-MM-dd HH:mm:ss")
                + ",订单状态为:" + orderStatus + ",支付状态为:" + payStatus + "的订单");
        // 查询
        List<SfOrder> resList = sfOrderMapper.selectByStatusAndDate(expiraTime, orderStatus, payStatus);
        if(resList == null || resList.size() == 0)
            return null;
        return resList;
    }

    /**
     * 取消未支付订单
     *
     * @param sfOrder
     */
    @Transactional
    public void cancelUnPaySfOrder(SfOrder sfOrder) {
        try{
            // 重新根据id查询该订单
            sfOrder = sfOrderMapper.selectByPrimaryKey(sfOrder.getId());
            // 检查订单状态的有效性
            if(sfOrder.getOrderStatus() != 0){
                throw new BusinessException("订单状态不正确,订单号:" + sfOrder.getOrderCode()
                        + ",当前订单状态为:" + sfOrder.getOrderStatus());
            }
            if(sfOrder.getPayStatus() != 0){
                throw new BusinessException("订单支付状态不正确,订单号:" + sfOrder.getOrderCode()
                        + ",当前订单支付状态为:" + sfOrder.getPayStatus());
            }
            log.info("订单状态和支付状态校验通过!");
            // 修改订单的状态为已取消状态
            int result = sfOrderMapper.updateOrderCancelById(sfOrder.getId(), 3);
            if(result != 1){
                sfOrder = sfOrderMapper.selectByPrimaryKey(sfOrder.getId());
                throw new BusinessException("订单取消失败,订单此时状态为:" + sfOrder.getOrderStatus()
                        + ",支付状态为:" + sfOrder.getPayStatus());
            }
            // 插入订单操作记录
            SfOrderOperationLog oLog = new SfOrderOperationLog();
            oLog.setCreateMan(0L);
            oLog.setCreateTime(new Date());
            oLog.setSfOrderId(sfOrder.getId());
            // 取消状态
            oLog.setSfOrderStatus(2);
            oLog.setRemark("超过72小时未支付,系统自动取消");
            logMapper.insert(oLog);
        } catch (Exception e) {
            log.error("订单超72小时未支付订单取消失败," + e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * 超过7天自动收货
     *
     * @param sfOrder
     */
    public void confirmOrderReceive(SfOrder sfOrder) {
        //SfOrder sfOrder = sfOrderMapper.selectByPrimaryKey(orderId);
        // 进行订单分润和代理商销售额、收入计算
        sfUserAccountService.countingSfOrder(sfOrder);
        // 进行订单状态修改
        sfOrder.setOrderStatus(3);
        sfOrderMapper.updateByPrimaryKey(sfOrder);
        SfOrderOperationLog sfOrderOperationLog = new SfOrderOperationLog();
        sfOrderOperationLog.setCreateMan(sfOrder.getUserId());
        sfOrderOperationLog.setCreateTime(new Date());
        sfOrderOperationLog.setSfOrderStatus(BOrderStatus.Complete.getCode());
        sfOrderOperationLog.setSfOrderId(sfOrder.getId());
        sfOrderOperationLog.setRemark("订单完成");
        logMapper.insert(sfOrderOperationLog);
    }
}
