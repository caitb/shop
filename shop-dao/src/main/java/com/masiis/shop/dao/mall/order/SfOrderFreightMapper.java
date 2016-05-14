/*
 * SfOrderFreightMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-08 Created
 */
package com.masiis.shop.dao.mall.order;

import com.masiis.shop.dao.po.SfOrderFreight;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SfOrderFreightMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfOrderFreight record);

    SfOrderFreight selectByPrimaryKey(Long id);

    List<SfOrderFreight> selectAll();

    int updateByPrimaryKey(SfOrderFreight record);

    List<SfOrderFreight> selectByOrderId(Long OrderId);
}