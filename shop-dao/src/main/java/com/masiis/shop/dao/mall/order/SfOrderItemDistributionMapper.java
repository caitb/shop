/*
 * SfOrderItemDistributionMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-08 Created
 */
package com.masiis.shop.dao.mall.order;

import com.masiis.shop.dao.po.SfOrderItemDistribution;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SfOrderItemDistributionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfOrderItemDistribution record);

    SfOrderItemDistribution selectByPrimaryKey(Long id);

    List<SfOrderItemDistribution> selectAll();

    int updateByPrimaryKey(SfOrderItemDistribution record);

    int selectCountByCondition(SfOrderItemDistribution record);

    /**
     * 根据orderitemid来查询商品分润记录
     *
     * @param itemId
     * @return
     */
    List<SfOrderItemDistribution> selectBySfOrderItemId(Long itemId);
    /**
     * 根据orderId查询商品分润记录
     * @author hanzengzhi
     * @date 2016/5/18 11:58
     */
    List<SfOrderItemDistribution> selectBySfOrderId(Long orderId);
}