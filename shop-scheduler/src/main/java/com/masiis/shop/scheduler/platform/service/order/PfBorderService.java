package com.masiis.shop.scheduler.platform.service.order;

import com.masiis.shop.common.enums.BOrder.BOrderStatus;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.dao.platform.order.PfBorderItemMapper;
import com.masiis.shop.dao.platform.order.PfBorderMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuStockMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfBorder;
import com.masiis.shop.dao.po.PfBorderItem;
import com.masiis.shop.dao.po.PfUserSkuStock;
import com.masiis.shop.scheduler.platform.service.product.PfUserSkuStockService;
import com.masiis.shop.scheduler.platform.service.user.ComUserAccountService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by lzh on 2016/3/23.
 */
@Service
public class PfBorderService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private PfBorderMapper borderMapper;
    @Resource
    private ComUserAccountService comUserAccountService;
    @Resource
    private BOrderOperationLogService bOrderOperationLogService;


    public List<PfBorder> findListByStatusAndDate(Date expiraTime, Integer orderStatus, Integer payStatus) {
        log.info("查询创建时间大于:" + DateUtil.Date2String(expiraTime, "yyyy-MM-dd HH:mm:ss")
                + ",订单状态为:" + orderStatus + ",支付状态为:" + payStatus + "的订单");
        // 查询
        List<PfBorder> resList = borderMapper.selectByStatusAndDate(expiraTime, orderStatus, payStatus);
        if (resList == null || resList.size() == 0)
            return null;
        return resList;
    }

    /**
     * 取消未支付订单
     *
     * @param bOrder
     */
    @Transactional
    public void cancelUnPayBOrder(PfBorder bOrder) {
        try {
            // 重新根据id查询该订单
            bOrder = borderMapper.selectByPrimaryKey(bOrder.getId());
            // 检查订单状态的有效性
            if (bOrder.getOrderStatus() != 0) {
                throw new BusinessException("订单状态不正确,订单号:" + bOrder.getOrderCode()
                        + ",当前订单状态为:" + bOrder.getOrderStatus());
            }
            if (bOrder.getPayStatus() != 0) {
                throw new BusinessException("订单支付状态不正确,订单号:" + bOrder.getOrderCode()
                        + ",当前订单支付状态为:" + bOrder.getPayStatus());
            }
            log.info("订单状态和支付状态校验通过!");
            // 修改订单的状态为已取消状态
            int result = borderMapper.updateOrderCancelById(bOrder.getId());
            if (result != 1) {
                bOrder = borderMapper.selectByPrimaryKey(bOrder.getId());
                throw new BusinessException("订单取消失败,订单此时状态为:" + bOrder.getOrderStatus()
                        + ",支付状态为:" + bOrder.getPayStatus());
            }
            // 插入订单操作记录
            bOrderOperationLogService.insertBOrderOperationLog(bOrder,"超过72小时未支付,系统自动取消");
        } catch (Exception e) {
            log.error("订单超72小时未支付订单取消失败," + e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * 订单发货7天后自动收货
     *
     * @param bOrder
     */
    @Transactional
    public void confirmOrderReceive(PfBorder bOrder) {
        if (bOrder == null) {
            throw new BusinessException("订单为空对象");
        }
        if (bOrder.getPayStatus() != 1) {
            throw new BusinessException("订单还未支付怎么能完成呢？");
        }
        bOrder.setOrderStatus(BOrderStatus.Complete.getCode());
        borderMapper.updateById(bOrder);
        //添加订单日志
        bOrderOperationLogService.insertBOrderOperationLog(bOrder, "订单完成");
        //订单类型(0代理1补货2拿货)
        if (bOrder.getOrderType() == 0 || bOrder.getOrderType() == 1) {
            comUserAccountService.countingByOrder(bOrder);
        }
    }
}
