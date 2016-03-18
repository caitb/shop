package com.masiis.shop.web.platform.service.product;

import com.masiis.shop.dao.platform.product.ComSkuImageMapper;
import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.platform.product.PfSkuStockMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuStockMapper;
import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.ComSkuImage;
import com.masiis.shop.dao.po.PfSkuStock;
import com.masiis.shop.dao.po.PfUserSkuStock;
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
    private PfSkuStockMapper pfSkuStockMapper;
    @Resource
    private PfUserSkuStockMapper pfUserSkuStockMapper;

    public ComSku getSkuById(Integer skuId) {
        return comSkuMapper.selectByPrimaryKey(skuId);
    }

    public ComSkuImage findComSkuImage(Integer skuId) {
        return comSkuImageMapper.selectDefaultImgBySkuId(skuId);
    }

    public int checkSkuStock(Integer skuId, int quantity, Long pUserId) {
        int n;
        if (pUserId == 0) {
            PfSkuStock pfSkuStock = pfSkuStockMapper.selectBySkuId(skuId);
            n = pfSkuStock.getStock() - pfSkuStock.getFrozenStock();
        } else {
            PfUserSkuStock pfUserSkuStock = pfUserSkuStockMapper.selectByUserIdAndSkuId(pUserId, skuId);
            n = pfUserSkuStock.getStock() - pfUserSkuStock.getFrozenStock();
        }
        return n - quantity;
    }
}
