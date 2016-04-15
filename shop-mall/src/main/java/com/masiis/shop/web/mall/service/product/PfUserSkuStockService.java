package com.masiis.shop.web.mall.service.product;

import com.masiis.shop.dao.platform.user.PfUserSkuStockMapper;
import com.masiis.shop.dao.po.PfUserSkuStock;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by hzz on 2016/4/14.
 */
@Service
public class PfUserSkuStockService {

    @Resource
    private PfUserSkuStockMapper pfUserSkuStockMapper;

    public PfUserSkuStock selectByUserIdAndSkuId(Long userId, Integer skuId){
        return pfUserSkuStockMapper.selectByUserIdAndSkuId(userId,skuId);
    }
    /**
     * 更新库存
     * @author hanzengzhi
     * @date 2016/4/14 15:45
     */
    public int update(PfUserSkuStock skuStock){
       return pfUserSkuStockMapper.updateByIdAndVersion(skuStock);
    }
}
