/*
 * PfBorderConsigneeMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package com.masiis.shop.dao.platform.order;

import com.masiis.shop.dao.po.PfBorderConsignee;
import java.util.List;

public interface PfBorderConsigneeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PfBorderConsignee record);

    PfBorderConsignee selectByPrimaryKey(Long id);

    List<PfBorderConsignee> selectAll();

    int updateByPrimaryKey(PfBorderConsignee record);
}