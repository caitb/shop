package com.masiis.shop.scheduler.platform.service.order;

import com.masiis.shop.common.enums.platform.BOrderShipStatus;
import com.masiis.shop.common.enums.platform.BOrderStatus;
import com.masiis.shop.common.enums.platform.BOrderType;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.dao.platform.order.PfBorderItemMapper;
import com.masiis.shop.dao.platform.order.PfBorderMapper;
import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.scheduler.platform.service.user.ComUserAccountService;
import com.masiis.shop.scheduler.platform.service.user.PfUserUpgradeNoticeService;
import com.masiis.shop.scheduler.utils.wx.WxPFNoticeUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    @Resource
    private ComUserMapper comUserMapper;
    @Resource
    private PfBorderItemMapper pfBorderItemMapper;
    @Resource
    private ComSkuMapper skuMapper;


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

    /**
     * 订单取消通知发送
     *
     * @param pfBorder
     * @param day
     */
    public void sendWxNoitceByCancelBorder(PfBorder pfBorder, int day) {
        ComUser user = comUserMapper.selectByPrimaryKey(pfBorder.getUserId());
        List<PfBorderItem> items = pfBorderItemMapper.selectAllByOrderId(pfBorder.getId());
        ComSku sku = skuMapper.findBySkuId(items.get(0).getSkuId());
        String[] params = {
                pfBorder.getOrderCode(),
                sku.getName(),
                items.get(0).getQuantity() + "",
                NumberFormat.getCurrencyInstance(Locale.CHINA).format(pfBorder.getOrderAmount())
        };
        if(day == 2) {
            WxPFNoticeUtils.getInstance().orderUnpayTwoDayCancelNotice(user, params, null);
        } else if(day == 3) {
            WxPFNoticeUtils.getInstance().orderUnpayThreeDayCancelNotice(user, params, null);
        } else if(day == 7) {
            WxPFNoticeUtils.getInstance().orderUnpaySevenDayCancelNotice(user, params, null);
        }
    }
}
