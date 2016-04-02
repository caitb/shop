package com.masiis.shop.admin.service.product;

import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.platform.product.PfSkuStockMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuStockMapper;
import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.PfSkuStock;
import com.masiis.shop.dao.po.PfUserSkuStock;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by caitingbiao on 2016/3/2.
 */
@Service
public class SkuService {

    @Resource
    private ComSkuMapper comSkuMapper;
    @Resource
    private PfSkuStockMapper pfSkuStockMapper;
    @Resource
    private PfUserSkuStockMapper pfUserSkuStockMapper;
    /**
     * 根据id查找商品
     * @param id
     * @return
     */
    public ComSku findById(Integer id){
        return comSkuMapper.selectById(id);
    }
    /**
     * 根据条件查询商品
     * @param comSku
     * @return
     */
    public List<ComSku> listByCondition(ComSku comSku){
        return comSkuMapper.selectByCondition(comSku);
    }
    /**
     * 判断库存是否足够
     * @author ZhaoLiang
     * @date 2016/4/1 16:19
     */
    public int checkSkuStock(Integer skuId, int quantity, Long pUserId) {
        int a;
        if (pUserId != 0) {
            PfUserSkuStock pfUserSkuStock = pfUserSkuStockMapper.selectByUserIdAndSkuId(pUserId, skuId);
            a = pfUserSkuStock.getStock() - pfUserSkuStock.getFrozenStock();
        } else {
            PfSkuStock pfSkuStock = pfSkuStockMapper.selectBySkuId(skuId);
            a = pfSkuStock.getStock() - pfSkuStock.getFrozenStock();
        }
        return a - quantity;
    }
}
