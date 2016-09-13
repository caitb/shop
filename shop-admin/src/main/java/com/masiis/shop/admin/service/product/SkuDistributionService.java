package com.masiis.shop.admin.service.product;

import com.masiis.shop.dao.platform.product.SfSkuDistributionMapper;
import com.masiis.shop.dao.po.SfSkuDistribution;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 代理分润
 * Created by cai_tb on 16/9/13.
 */
@Service
public class SkuDistributionService {

    @Resource
    private SfSkuDistributionMapper sfSkuDistributionMapper;

    /**
     * 获取商品代理分润
     * @param skuId
     * @return
     */
    public List<SfSkuDistribution> listBySkuId(Integer skuId){
        return sfSkuDistributionMapper.selectBySkuId(skuId);
    }
}
