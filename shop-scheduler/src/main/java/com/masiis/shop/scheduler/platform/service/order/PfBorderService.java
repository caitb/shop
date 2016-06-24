package com.masiis.shop.scheduler.platform.service.order;

import com.masiis.shop.common.enums.BOrder.BOrderShipStatus;
import com.masiis.shop.common.enums.BOrder.BOrderStatus;
import com.masiis.shop.common.enums.BOrder.BOrderType;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.dao.platform.order.PfBorderItemMapper;
import com.masiis.shop.dao.platform.order.PfBorderMapper;
import com.masiis.shop.dao.platform.order.PfBorderRecommenRewardMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuStockMapper;
import com.masiis.shop.dao.platform.user.PfUserUpgradeNoticeMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.scheduler.platform.service.product.PfUserSkuStockService;
import com.masiis.shop.scheduler.platform.service.user.ComUserAccountService;
import com.masiis.shop.scheduler.platform.service.user.PfUserUpgradeNoticeService;
import com.masiis.shop.scheduler.utils.wx.WxPFNoticeUtils;
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
    @Resource
    private PfUserUpgradeNoticeService pfUserUpgradeNoticeService;


    public List<PfBorder> findListByStatusAndDate(Date expiraTime, Integer orderStatus, Integer payStatus) {
        log.info("查询创建时间小于:" + DateUtil.Date2String(expiraTime, "yyyy-MM-dd HH:mm:ss")
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
        //拿货方式(0未选择1平台代发2自己发货)
        if (bOrder.getSendType() == 1) {
            if (!bOrder.getOrderStatus().equals(BOrderStatus.accountPaid.getCode())
                    && !bOrder.getOrderStatus().equals(BOrderStatus.Ship.getCode())) {
                throw new BusinessException("订单状态异常");
            }
        } else if (bOrder.getSendType() == 2) {
            if (!bOrder.getOrderStatus().equals(BOrderStatus.Ship.getCode())
                    && !bOrder.getOrderStatus().equals(BOrderStatus.Ship.getCode())) {
                throw new BusinessException("订单状态异常");
            }
        } else {
            throw new BusinessException("订单拿货方式异常");
        }
        bOrder.setOrderStatus(BOrderStatus.Complete.getCode());
        bOrder.setShipStatus(BOrderShipStatus.Receipt.getCode());//已收货
        bOrder.setIsReceipt(1);
        bOrder.setReceiptTime(new Date());//收货时间
        borderMapper.updateById(bOrder);
        //添加订单日志
        bOrderOperationLogService.insertBOrderOperationLog(bOrder, "订单自动收货完成");
        //订单类型(0代理1补货2拿货)
        if (bOrder.getOrderType() == 0 || bOrder.getOrderType() == 1 || bOrder.getOrderType() == 3) {
            comUserAccountService.countingByOrder(bOrder);
        }
    }

    /**
     * 自动取消7天未支付线下支付订单
     *
     * @param bOrder
     */
    @Transactional
    public void cancelOfflinePayBOrder(PfBorder bOrder) {
        try {
            // 重新根据id查询该订单
            bOrder = borderMapper.selectByPrimaryKey(bOrder.getId());
            // 检查订单状态的有效性
            if (bOrder.getOrderStatus() != BOrderStatus.offLineNoPay.getCode().intValue()) {
                throw new BusinessException("订单状态不正确,订单号:" + bOrder.getOrderCode()
                        + ",当前订单状态为:" + bOrder.getOrderStatus());
            }
            if (bOrder.getPayStatus() != 0) {
                throw new BusinessException("订单支付状态不正确,订单号:" + bOrder.getOrderCode()
                        + ",当前订单支付状态为:" + bOrder.getPayStatus());
            }
            log.info("订单状态和支付状态校验通过!");
            // 修改订单的状态为已取消状态
            int result = borderMapper.updateOfflineBOrderCancelById(bOrder.getId());
            if (result != 1) {
                bOrder = borderMapper.selectByPrimaryKey(bOrder.getId());
                throw new BusinessException("订单取消失败,订单此时状态为:" + bOrder.getOrderStatus()
                        + ",支付状态为:" + bOrder.getPayStatus());
            }
            // 插入订单操作记录
            bOrderOperationLogService.insertBOrderOperationLog(bOrder,"超过7天未支付线下支付订单,系统自动取消");
        } catch (Exception e) {
            log.error("订单超7天未支付线下支付订单取消失败," + e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    public List<PfBorder> findUnUpgradeByStatusAndDate(Date expiraTime, Integer orderStatus, Integer payStatus) {
        log.info("查询创建时间小于:" + DateUtil.Date2String(expiraTime, "yyyy-MM-dd HH:mm:ss")
                + ",订单状态为:" + orderStatus + ",支付状态为:" + payStatus + ",订单类型不是"
                + BOrderType.UPGRADE.getCode() + "的订单");
        // 查询
        List<PfBorder> resList = borderMapper.selectUnUpgradeByStatusAndDate(expiraTime, orderStatus, payStatus, BOrderType.UPGRADE.getCode());
        if (resList == null || resList.size() == 0)
            return null;
        return resList;
    }

    /**
     * 查询2天未支付升级订单
     *
     * @param expiraTime
     * @param orderStatus
     * @param payStatus
     * @return
     */
    public List<PfBorder> findUpgradeByStatusAndDate(Date expiraTime, Integer orderStatus, Integer payStatus) {
        log.info("查询创建时间小于:" + DateUtil.Date2String(expiraTime, "yyyy-MM-dd HH:mm:ss")
                + ",订单状态为:" + orderStatus + ",支付状态为:" + payStatus + ",订单类型是"
                + BOrderType.UPGRADE.getCode() + "的订单");
        // 查询
        List<PfBorder> resList = borderMapper
                .selectByStatusAndDateAndType(expiraTime, orderStatus, payStatus, BOrderType.UPGRADE.getCode());
        if (resList == null || resList.size() == 0)
            return null;
        return resList;
    }

}
