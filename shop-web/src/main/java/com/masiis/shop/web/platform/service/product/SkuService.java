package com.masiis.shop.web.platform.service.product;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.platform.product.ComSkuExtensionMapper;
import com.masiis.shop.dao.platform.product.ComSkuImageMapper;
import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.platform.product.PfSkuStockMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuStockMapper;
import com.masiis.shop.dao.po.*;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import com.sun.org.apache.xpath.internal.Expression;
import org.springframework.expression.ExpressionException;
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
    private PfSkuStockMapper pfSkuStockMapper;
    @Resource
    private PfUserSkuStockMapper pfUserSkuStockMapper;
    @Resource
    private ComUserMapper comUserMapper;

    public ComSku getSkuById(Integer skuId) {
        return comSkuMapper.selectByPrimaryKey(skuId);
    }

    public ComSkuImage findComSkuImage(Integer skuId) {
        return comSkuImageMapper.selectDefaultImgBySkuId(skuId);
    }

    /**
     * 判断库存是否足够
     *
     * @author ZhaoLiang
     * @date 2016/4/1 16:19
     */
    public int checkSkuStock(Integer skuId, int quantity, Long pUserId) throws Exception {
        int n = 0;
        if (pUserId == 0) {
            PfSkuStock pfSkuStock = pfSkuStockMapper.selectBySkuId(skuId);
            n = pfSkuStock.getStock() - pfSkuStock.getFrozenStock();
        } else {
            ComUser comUser = comUserMapper.selectByPrimaryKey(pUserId);
            if (comUser == null) {
                throw new BusinessException("找不到该用户");
            } else {
                PfUserSkuStock pfUserSkuStock = pfUserSkuStockMapper.selectByUserIdAndSkuId(pUserId, skuId);
                //拿货方式: 0,未选择; 1,平台代发; 2,自己发货
                if (comUser.getSendType() == 1) {
                    n = pfUserSkuStock.getStock() - pfUserSkuStock.getFrozenStock();
                } else {
                    n = pfUserSkuStock.getCustomStock();
                }
            }
        }
        return n - quantity;
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


}
