/*
 * SfOrderMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-08 Created
 */
package com.masiis.shop.dao.mall.order;

import com.masiis.shop.dao.po.SfOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SfOrderManageMapper {
    List<SfOrder> selectByUserId(@Param("userId") Long userId, @Param("orderStatus") Integer orderStatus, @Param("shopId") Long shopId);
}