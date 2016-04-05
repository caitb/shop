package com.masiis.shop.web.platform.service.order;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.beans.order.StockManage;
import com.masiis.shop.dao.platform.order.*;
import com.masiis.shop.dao.platform.product.PfSkuAgentMapper;
import com.masiis.shop.dao.platform.product.PfSkuStockMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuStockMapper;
import com.masiis.shop.dao.po.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 更改库存
 * @author muchaofeng
 * @date 2016/4/2 10:31
 */

@Service
@Transactional
public class BorderSkuStockService {

    @Resource
    private PfBorderItemMapper pfBorderItemMapper;
    @Resource
    private PfSkuStockMapper pfSkuStockMapper;
    @Resource
    private PfUserSkuStockMapper pfUserSkuStockMapper;

    /**
     * 更新出货库存
     *
     * @author muchaofeng
     * @date 2016/3/21 14:35
     */
    public  void  updateStock(PfBorder pfBorder, ComUser user) {
        PfUserSkuStock pfUserSkuStock = null;
        PfSkuStock pfSkuStock = null;
        for (PfBorderItem pfBorderItem : pfBorderItemMapper.selectAllByOrderId(pfBorder.getId())) {
            if (pfBorder.getUserPid() == 0) {
                pfSkuStock = pfSkuStockMapper.selectBySkuId(pfBorderItem.getSkuId());
                if (pfSkuStock.getStock() - pfBorderItem.getQuantity() >= 0 && pfSkuStock.getFrozenStock() - pfBorderItem.getQuantity() >= 0) {
                    pfSkuStock.setStock(pfSkuStock.getStock() - pfBorderItem.getQuantity());
                    pfSkuStock.setFrozenStock(pfSkuStock.getFrozenStock() - pfBorderItem.getQuantity());
                    if (pfSkuStockMapper.updateByIdAndVersion(pfSkuStock) == 0) {
                        throw new BusinessException("并发修改库存失败");
                    }
                } else {
                    throw new BusinessException(pfBorderItem.getSkuName() + "当前库存异常");
                }
            } else {
                pfUserSkuStock = pfUserSkuStockMapper.selectByUserIdAndSkuId(user.getId(), pfBorderItem.getSkuId());
                if (pfUserSkuStock.getStock() - pfBorderItem.getQuantity() >= 0 && pfUserSkuStock.getFrozenStock() - pfBorderItem.getQuantity() >= 0) {
                    pfUserSkuStock.setFrozenStock(pfUserSkuStock.getFrozenStock() - pfBorderItem.getQuantity());
                    pfUserSkuStock.setStock(pfUserSkuStock.getStock() - pfBorderItem.getQuantity());
                    if (pfUserSkuStockMapper.updateByIdAndVersion(pfUserSkuStock) == 0) {
                        throw new BusinessException("并发修改库存失败");
                    }
                } else {
                    throw new BusinessException(pfBorderItem.getSkuName() + "当前库存异常");
                }
            }
        }
    }

    /**
     * 更新进货库存
     *
     * @author muchaofeng
     * @date 2016/3/21 16:22
     */
    public void updateGetStock(PfBorder pfBorder, ComUser user) {
        PfUserSkuStock pfUserSkuStock = null;
        for (PfBorderItem pfBorderItem : pfBorderItemMapper.selectAllByOrderId(pfBorder.getId())) {
            pfUserSkuStock = pfUserSkuStockMapper.selectByUserIdAndSkuId(user.getId(), pfBorderItem.getSkuId());
            if (pfUserSkuStock != null) {
                pfUserSkuStock.setStock(pfUserSkuStock.getStock() + pfBorderItem.getQuantity());
                if (pfUserSkuStockMapper.updateByIdAndVersion(pfUserSkuStock) == 0) {
                    throw new BusinessException("并发修改库存失败");
                }
            }
        }
    }
    /**
     * 商品库存
     * @author muchaofeng
     * @date 2016/4/5 12:14
     */

    public List<StockManage> totalGetStock(PfBorder pfBorder, ComUser user) {
        PfUserSkuStock pfUserSkuStock = null;
        StockManage stockManage =null;
        List<StockManage> stockManages=new ArrayList<>();
        for (PfBorderItem pfBorderItem : pfBorderItemMapper.selectAllByOrderId(pfBorder.getId())) {
            pfUserSkuStock = pfUserSkuStockMapper.selectByUserIdAndSkuId(user.getId(), pfBorderItem.getSkuId());
            stockManage.setSkuName(pfBorderItem.getSkuName());
            stockManage.setStockNum(pfUserSkuStock.getStock());
            stockManages.add(stockManage);
        }
        return stockManages;
    }
}
