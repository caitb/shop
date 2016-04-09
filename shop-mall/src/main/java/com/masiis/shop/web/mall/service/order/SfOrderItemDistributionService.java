package com.masiis.shop.web.mall.service.order;

import com.github.pagehelper.PageHelper;
import com.masiis.shop.dao.mall.order.SfOrderItemDistributionExtendMapper;
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
    @Autowired
    private SfOrderItemDistributionExtendMapper sfOrderItemDistributionExtendMapper;

    private final Logger log = Logger.getLogger(SfOrderItemDistributionService.class);

    /**
     * 根据条件查询小铺订单商品分润 数量
     * @param record
     * @return
     */
    public int findCountByCondition(SfOrderItemDistribution record){
        return sfOrderItemDistributionMapper.selectCountByCondition(record);
    }

    /**
     * 通过userId查询佣金记录
     * @param userId        userId
     * @param currentPage   当前页
     * @param pageSize      每页数量
     * @return
     */
    public List<SfOrderItemDistribution> findCommissionRecordByUserIdLimitPage(Long userId,int currentPage,int pageSize){
        //当当前页或者每页数量为0时 不进行分页查询
        if (currentPage == 0 || pageSize == 0){
            return sfOrderItemDistributionExtendMapper.selectCommissionRecordByUserId(userId);
        }
        PageHelper.startPage(currentPage,pageSize);
        return sfOrderItemDistributionExtendMapper.selectCommissionRecordByUserId(userId);
    }
}
