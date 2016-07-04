/*
 * SfGorderConsigneeMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-04 Created
 */
package com.masiis.shop.dao.mall.promotion;

import com.masiis.shop.dao.po.SfGorderConsignee;
import java.util.List;

public interface SfGorderConsigneeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfGorderConsignee record);

    SfGorderConsignee selectByPrimaryKey(Long id);

    List<SfGorderConsignee> selectAll();

    int updateByPrimaryKey(SfGorderConsignee record);
}