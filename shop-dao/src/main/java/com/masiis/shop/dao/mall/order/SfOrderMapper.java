/*
 * SfOrderMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-08 Created
 */
package com.masiis.shop.dao.mall.order;

import com.masiis.shop.dao.po.SfOrder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SfOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfOrder record);

    SfOrder selectByPrimaryKey(Long id);

    List<SfOrder> selectAll();

    int updateByPrimaryKey(SfOrder record);

    SfOrder selectByOrderCode(String orderCode);

    /**
     * 统计店铺订单量
     * @param shopId
     * @return
     */
    Integer countByShopId(Long shopId);

}