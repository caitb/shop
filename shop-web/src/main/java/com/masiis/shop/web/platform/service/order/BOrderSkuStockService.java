package com.masiis.shop.web.platform.service.order;

import com.masiis.shop.common.enums.product.SkuStockLogType;
import com.masiis.shop.common.enums.product.UserSkuStockLogType;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.beans.order.StockManage;
import com.masiis.shop.dao.mall.order.SfOrderItemMallMapper;
import com.masiis.shop.dao.mall.user.SfUserRelationMapper;
import com.masiis.shop.dao.platform.order.*;
import com.masiis.shop.dao.platform.product.PfSkuAgentMapper;
import com.masiis.shop.dao.platform.product.PfSkuStockMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuStockMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.service.product.PfSkuStockService;
import com.masiis.shop.web.platform.service.product.PfUserSkuStockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
     * 更新出货库存(小铺发货)
     *
     * @author muchaofeng
     * @date 2016/3/21 14:35
     */
    public void updateOrderStock(SfOrder sfOrder, ComUser user) {
        PfUserSkuStock pfUserSkuStock = null;
        PfSkuStock pfSkuStock = null;
        SfUserRelation sfUserRelation = sfUserRelationMapper.getSfUserRelationByUserId(user.getId());
        if (sfUserRelation == null) {
            throw new BusinessException("用户关系异常");
        }
        ComUser userPid = comUserMapper.selectByPrimaryKey(sfUserRelation.getUserPid());
        if (userPid == null) {
            throw new BusinessException("用户上级为空");
        }
        for (SfOrderItem sfOrderItem : sfOrderItemMallMapper.selectBySfOrderId(sfOrder.getId())) {
            if (userPid.getId() == 0) {
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
