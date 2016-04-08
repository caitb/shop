/*
 * SfOrderConsigneeMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-08 Created
 */
package com.masiis.shop.dao.mall.order;

import com.masiis.shop.dao.po.SfOrderConsignee;
import java.util.List;

public interface SfOrderConsigneeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfOrderConsignee record);

    SfOrderConsignee selectByPrimaryKey(Long id);

    List<SfOrderConsignee> selectAll();

    int updateByPrimaryKey(SfOrderConsignee record);
}