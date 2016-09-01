package com.masiis.shop.web.platform.service.order;

import com.masiis.shop.common.enums.platform.SkuStockLogType;
import com.masiis.shop.common.enums.platform.UserSkuStockLogType;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.mall.order.SfOrderItemMallMapper;
import com.masiis.shop.dao.mall.user.SfUserRelationMapper;
import com.masiis.shop.dao.platform.order.*;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.service.product.PfSkuStockService;
import com.masiis.shop.web.platform.service.product.PfUserSkuStockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 更改库存
 *
 * @author muchaofeng
 * @date 2016/4/2 10:31
 */

@Service
@Transactional
public class BOrderSkuStockService {

    @Resource
    private PfBorderItemMapper pfBorderItemMapper;
    @Resource
    private PfSkuStockService pfSkuStockService;
    @Resource
    private PfUserSkuStockService pfUserSkuStockService;
    @Resource
    private SfOrderItemMallMapper sfOrderItemMallMapper;
    @Resource
    private SfUserRelationMapper sfUserRelationMapper;
    @Resource
    private ComUserMapper comUserMapper;

    /**
     * 更新出货库存(代理自己拿货发货)
     *
     * @author muchaofeng
     * @date 2016/3/21 14:35
     */
    public void updateStock(PfBorder pfBorder, ComUser user) {
        PfUserSkuStock pfUserSkuStock = null;
        PfSkuStock pfSkuStock = null;
        for (PfBorderItem pfBorderItem : pfBorderItemMapper.selectAllByOrderId(pfBorder.getId())) {
            if (pfBorder.getUserPid() == 0) {
                pfSkuStock = pfSkuStockService.selectBySkuId(pfBorderItem.getSkuId());
                if (pfSkuStock.getStock() - pfBorderItem.getQuantity() >= 0 && pfSkuStock.getFrozenStock() - pfBorderItem.getQuantity() >= 0) {
                    pfSkuStockService.updateSkuStockWithLog(pfBorderItem.getQuantity(), pfSkuStock, pfBorder.getId(), SkuStockLogType.downAgent);
                } else {
                    throw new BusinessException(pfBorderItem.getSkuName() + "当前库存异常");
                }
            } else {
                pfUserSkuStock = pfUserSkuStockService.selectByUserIdAndSkuId(user.getId(), pfBorderItem.getSkuId());
                pfUserSkuStock.getCustomStock();
                if (pfUserSkuStock.getStock() - pfBorderItem.getQuantity() >= 0 && pfUserSkuStock.getFrozenStock() - pfBorderItem.getQuantity() >= 0) {
                    pfUserSkuStockService.updateUserSkuStockWithLog(pfBorderItem.getQuantity(), pfUserSkuStock, pfBorder.getId(), UserSkuStockLogType.downAgent);
                } else {
                    throw new BusinessException(pfBorderItem.getSkuName() + "当前库存异常");
                }
            }
        }
    }

    /**
     * 更新出货库存(平台发货)
     *
     * @author muchaofeng
     * @date 2016/3/21 14:35
     */
    public void updateOrderStock(SfOrder sfOrder, ComUser user) {
        PfUserSkuStock pfUserSkuStock = null;
        //拿货方式(0未选择1平台代发2自己发货)
        if (sfOrder.getSendType()==1 && sfOrder.getSendMan()==0L) {//if (sfOrder.getSendType().equals(1) && sfOrder.getSendMan().equals(0)) {
            for (SfOrderItem sfOrderItem : sfOrderItemMallMapper.selectBySfOrderId(sfOrder.getId())) {
                if (sfOrder.getShopUserId() == 0) {
                    throw new BusinessException("小铺PID不能为0");
                } else {
                    pfUserSkuStock = pfUserSkuStockService.selectByUserIdAndSkuId(user.getId(), sfOrderItem.getSkuId());
                    if (pfUserSkuStock.getStock() - sfOrderItem.getQuantity() >= 0 && pfUserSkuStock.getFrozenStock() - sfOrderItem.getQuantity() >= 0) {
                        pfUserSkuStockService.updateUserSkuStockWithLog(sfOrderItem.getQuantity(), pfUserSkuStock, sfOrder.getId(), UserSkuStockLogType.shopOrder);
                    } else {
                        throw new BusinessException(sfOrderItem.getSkuName() + "当前库存异常");
                    }
                }
            }
        } else if (sfOrder.getSendType()==2 && sfOrder.getSendMan()>0L) {//(sfOrder.getSendType().equals(2) && sfOrder.getSendMan() > 0) {
            for (SfOrderItem sfOrderItem : sfOrderItemMallMapper.selectBySfOrderId(sfOrder.getId())) {
                if (sfOrder.getShopUserId() == 0) {
                    throw new BusinessException("小铺PID不能为0");
                } else {
                    pfUserSkuStock = pfUserSkuStockService.selectByUserIdAndSkuId(user.getId(), sfOrderItem.getSkuId());
                    if (pfUserSkuStock.getCustomStock() - sfOrderItem.getQuantity() >= 0 && pfUserSkuStock.getFrozenCustomStock() - sfOrderItem.getQuantity() >= 0) {
                        pfUserSkuStock.setCustomStock(pfUserSkuStock.getCustomStock() - sfOrderItem.getQuantity());
                        pfUserSkuStock.setFrozenCustomStock(pfUserSkuStock.getFrozenCustomStock() - sfOrderItem.getQuantity());
                        pfUserSkuStockService.updateByIdAndVersions(pfUserSkuStock);
                    } else {
                        throw new BusinessException(sfOrderItem.getSkuName() + "当前库存异常");
                    }
                }
            }
        } else {
            throw new BusinessException("订单状态发货类型状态异常！");
        }
    }


    /**
     * 查询商品库存
     *
     * @param userId
     * @param skuId
     * @return
     */
    public PfUserSkuStock getUserSkuStockByUserIdAndSkuId(Long userId, int skuId) {
        return pfUserSkuStockService.selectByUserIdAndSkuId(userId, skuId);
    }
}
