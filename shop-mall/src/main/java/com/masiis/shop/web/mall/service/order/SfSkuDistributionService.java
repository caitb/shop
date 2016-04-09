package com.masiis.shop.web.mall.service.order;

import com.masiis.shop.dao.platform.product.SfSkuDistributionMapper;
import com.masiis.shop.dao.po.SfSkuDistribution;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hzz on 2016/4/9.
 */
@Service
public class SfSkuDistributionService {

    @Resource
    private SfSkuDistributionMapper sfSkuDistributionMapper;

    @Transactional(propagation = Propagation.REQUIRED,readOnly = true)
    public List<SfSkuDistribution> getSfSkuDistributionBySkuId(Integer skuId){
        return sfSkuDistributionMapper.selectBySkuId(skuId);
    }
}
