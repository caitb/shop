package com.masiis.shop.scheduler.platform.service.order;

import com.masiis.shop.common.enums.platform.BOrderShipStatus;
import com.masiis.shop.common.enums.platform.BOrderStatus;
import com.masiis.shop.common.enums.platform.SkuStockLogType;
import com.masiis.shop.common.enums.platform.UserSkuStockLogType;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.platform.order.PfBorderItemMapper;
import com.masiis.shop.dao.platform.order.PfBorderMapper;
import com.masiis.shop.dao.po.PfBorder;
import com.masiis.shop.dao.po.PfBorderItem;
import com.masiis.shop.dao.po.PfSkuStock;
import com.masiis.shop.dao.po.PfUserSkuStock;
import com.masiis.shop.scheduler.platform.service.product.PfSkuStockService;
import com.masiis.shop.scheduler.platform.service.product.PfUserSkuStockService;
import com.masiis.shop.scheduler.platform.service.user.ComUserAccountService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * BOrderShipService
 *
 * @author ZhaoLiang
 * @date 2016/7/4
 */
@Service
@Transactional
public class BOrderShipService {
    private Logger logger = Logger.getLogger(this.getClass());
    @Resource
    private PfBorderMapper pfBorderMapper;
    @Resource
    private PfBorderItemMapper pfBorderItemMapper;
    @Resource
    private PfSkuStockService pfSkuStockService;
    @Resource
    private PfUserSkuStockService pfUserSkuStockService;
    @Resource
    private BOrderOperationLogService bOrderOperationLogService;
    @Resource
    private ComUserAccountService comUserAccountService;

    /**
     * 合伙订单发货和收货
     *
     * @param pfBorder
     */
    public void shipAndReceiptBOrder(PfBorder pfBorder) {
        shipBOrder(pfBorder);
        receiptBOrder(pfBorder);
    }

    /**
     * 合伙人订单发货
     * @param pfBorder
     */
    public void shipBOrder(PfBorder pfBorder) {
        if (!pfBorder.getOrderStatus().equals(BOrderStatus.WaitShip.getCode())) {
            throw new BusinessException("订单状态异常，订单号:" + pfBorder.getId() + "，订单状态:" + pfBorder.getOrderStatus() + "。");
        }
        for (PfBorderItem pfBorderItem : pfBorderItemMapper.selectAllByOrderId(pfBorder.getId())) {
            if (pfBorderItem.getQuantity() > 0) {
                logger.info("减少发货方库存和冻结库存 如果用户id是0操作平台库存");
                if (pfBorder.getUserPid() == 0) {
                    PfSkuStock pfSkuStock = pfSkuStockService.selectBySkuId(pfBorderItem.getSkuId());
                    if (pfSkuStock.getStock() < pfBorderItem.getQuantity()) {
                        throw new BusinessException("库存不足，操作失败");
                    }
                    if (pfSkuStock.getFrozenStock() < pfBorderItem.getQuantity()) {
                        throw new BusinessException("库存冻结不足，操作失败");
                    }
                    pfSkuStockService.updateSkuStockWithLog(pfBorderItem.getQuantity(), pfSkuStock, pfBorder.getId(), SkuStockLogType.downAgent);
                } else {
                    PfUserSkuStock parentSkuStock = pfUserSkuStockService.selectByUserIdAndSkuId(pfBorder.getUserPid(), pfBorderItem.getSkuId());
                    if (parentSkuStock.getStock() < pfBorderItem.getQuantity()) {
                        throw new BusinessException("库存不足，操作失败");
                    }
                    if (parentSkuStock.getFrozenStock() < pfBorderItem.getQuantity()) {
                        throw new BusinessException("库存冻结不足，操作失败");
                    }
                    pfUserSkuStockService.updateUserSkuStockWithLog(pfBorderItem.getQuantity(), parentSkuStock, pfBorder.getId(), UserSkuStockLogType.downAgent);
                }
            }
        }
        pfBorder.setShipStatus(BOrderShipStatus.Ship.getCode());
        pfBorder.setShipTime(new Date());
        pfBorder.setIsShip(1);
        pfBorder.setOrderStatus(BOrderStatus.Ship.getCode());
        pfBorderMapper.updateById(pfBorder);
        //添加订单日志
        bOrderOperationLogService.insertBOrderOperationLog(pfBorder, "订单发货");
    }

    /**
     * 合伙人订单收货
     * @param pfBorder
     */
    public void receiptBOrder(PfBorder pfBorder) {
        if (!pfBorder.getOrderStatus().equals(BOrderStatus.Ship.getCode())) {
            throw new BusinessException("订单状态异常，订单号:" + pfBorder.getId() + "，订单状态:" + pfBorder.getOrderStatus() + "。");
        }
        for (PfBorderItem pfBorderItem : pfBorderItemMapper.selectAllByOrderId(pfBorder.getId())) {
            if (pfBorderItem.getQuantity() > 0) {
                logger.info("增加收货方库存");
                PfUserSkuStock pfUserSkuStock = pfUserSkuStockService.selectByUserIdAndSkuId(pfBorder.getUserId(), pfBorderItem.getSkuId());
                pfUserSkuStockService.updateUserSkuStockWithLog(pfBorderItem.getQuantity(), pfUserSkuStock, pfBorder.getId(), UserSkuStockLogType.agent);
            }
        }
        pfBorder.setOrderStatus(BOrderStatus.Complete.getCode());//订单完成
        pfBorder.setShipStatus(BOrderShipStatus.Receipt.getCode());//已收货
        pfBorder.setIsReceipt(1);
        pfBorder.setReceiptTime(new Date());//收货时间
        pfBorderMapper.updateById(pfBorder);
        //添加订单日志
        bOrderOperationLogService.insertBOrderOperationLog(pfBorder, "订单完成");
        //订单类型(0代理1补货2拿货)
        if (pfBorder.getOrderType() == 0 || pfBorder.getOrderType() == 1 || pfBorder.getOrderType() == 3) {
            comUserAccountService.countingByOrder(pfBorder);
        }
    }

}
