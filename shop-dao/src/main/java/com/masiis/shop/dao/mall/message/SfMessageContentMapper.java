/*
 * SfMessageContentMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-05 Created
 */
package com.masiis.shop.dao.mall.message;

import com.masiis.shop.dao.po.SfMessageContent;
import java.util.List;

public interface SfMessageContentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfMessageContent record);

    SfMessageContent selectByPrimaryKey(Long id);

    List<SfMessageContent> selectAll();

    int updateByPrimaryKey(SfMessageContent record);
}