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

    /**
     * 根据skuId并升序排序，分销用
     * @author hanzengzhi
     * @date 2016/4/9 16:10
     */
    @Transactional(propagation = Propagation.REQUIRED,readOnly = true)
    public List<SfSkuDistribution> getSfSkuDistributionBySkuIdAndSortAsc(Integer skuId){
        return sfSkuDistributionMapper.selectBySkuIdAndSortAsc(skuId);
    }
}
