package com.masiis.shop.admin.service.order;

import com.masiis.shop.dao.platform.user.PfUserSkuStockMapper;
import com.masiis.shop.dao.po.PfUserSkuStock;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by cai_tb on 16/6/10.
 */
@Service
public class UserSkuStockService {

    @Resource
    private PfUserSkuStockMapper pfUserSkuStockMapper;

    /**
     * 查找商品库存
     * @param userId
     * @param skuId
     * @return
     */
    public PfUserSkuStock findByUserIdAndSkuId(Long userId, Integer skuId){
        return pfUserSkuStockMapper.selectByUserIdAndSkuId(userId, skuId);
    }
}
