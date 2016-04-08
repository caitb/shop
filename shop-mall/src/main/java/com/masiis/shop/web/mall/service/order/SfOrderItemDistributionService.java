package com.masiis.shop.web.mall.service.order;

import com.masiis.shop.dao.mall.order.SfOrderItemDistributionMapper;
import com.masiis.shop.dao.po.SfOrderItemDistribution;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 小铺订单商品分润Service
 * Created by wangbingjian on 2016/4/8.
 */
@Service
public class SfOrderItemDistributionService {

    @Autowired
    private SfOrderItemDistributionMapper sfOrderItemDistributionMapper;

    private final Logger log = Logger.getLogger(SfOrderItemDistributionService.class);

    /**
     * 根据条件查询小铺订单商品分润 数量
     * @param record
     * @return
     */
    public int findCountByCondition(SfOrderItemDistribution record){
        return sfOrderItemDistributionMapper.selectCountByCondition(record);
    }

    public List<SfOrderItemDistribution> findCommissionRecordByUserId(Long userId){
        return sfOrderItemDistributionMapper.selectCommissionRecordByUserId(userId);
    }
}
