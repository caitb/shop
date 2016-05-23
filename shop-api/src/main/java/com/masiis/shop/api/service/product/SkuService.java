package com.masiis.shop.api.service.product;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.platform.product.ComSkuExtensionMapper;
import com.masiis.shop.dao.platform.product.ComSkuImageMapper;
import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.platform.product.PfSkuStockMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuStockMapper;
import com.masiis.shop.dao.po.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * SkuService
 *
 * @author ZhaoLiang
 * @date 2016/3/7
 */
@Service
@Transactional
public class SkuService {

    @Resource
    private ComSkuMapper comSkuMapper;
    @Resource
    private ComSkuImageMapper comSkuImageMapper;
    @Resource
    private ComSkuExtensionMapper comSkuExtensionMapper;
    @Resource
    private PfSkuStockService pfSkuStockService;
    @Resource
    private PfUserSkuStockService pfUserSkuStockService;
    @Resource
    private ComUserMapper comUserMapper;

    public ComSku getSkuById(Integer skuId) {
        return comSkuMapper.selectByPrimaryKey(skuId);
    }

    public ComSkuImage findComSkuImage(Integer skuId) {
        return comSkuImageMapper.selectDefaultImgBySkuId(skuId);
    }


    /**
     * 检查库存状态
     *
     * @param skuId
     * @param quantity
     * @param pUserId
     * @return 0库存充足1库存不足进入排单2库存不足不可下单
     * @throws Exception
     */
    public int getSkuStockStatus(Integer skuId, int quantity, Long pUserId) {
        if (pUserId == 0) {
            PfSkuStock pfSkuStock = pfSkuStockService.selectBySkuId(skuId);
            //如果进入排单直接返回-quantity
            if (pfSkuStock.getIsQueue() == 1) {
                return 1;
            }
            if (pfSkuStock.getStock() - pfSkuStock.getFrozenStock() < quantity) {
                return 1;
            } else {
                return 0;
            }
        } else {
            ComUser comUser = comUserMapper.selectByPrimaryKey(pUserId);
            if (comUser == null) {
                throw new BusinessException("找不到该用户");
            } else {
                PfUserSkuStock pfUserSkuStock = pfUserSkuStockService.selectByUserIdAndSkuId(pUserId, skuId);
                //拿货方式: 0,未选择; 1,平台代发; 2,自己发货
                if (comUser.getSendType() == 1) {
                    if (pfUserSkuStock.getStock() - pfUserSkuStock.getFrozenStock() < quantity) {
                        return 1;
                    } else {
                        return 0;
                    }
                } else {
                    if (pfUserSkuStock.getCustomStock() < quantity) {
                        return 2;
                    } else {
                        return 0;
                    }
                }
            }
        }
    }

    /**
     * 根据skuId查找skuExtension
     *
     * @param skuId
     * @return
     */
    public ComSkuExtension findSkuExteBySkuId(Integer skuId) {
        return comSkuExtensionMapper.selectBySkuId(skuId);
    }

    /**
     * jjh
     * 查看排单flag
     */
    public PfSkuStock getPfSkuStockInfoBySkuId(Integer skuId) {
        return pfSkuStockService.selectBySkuId(skuId);
    }

}
