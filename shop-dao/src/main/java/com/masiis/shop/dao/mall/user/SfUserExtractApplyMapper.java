/*
 * SfUserExtractApplyMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-10 Created
 */
package com.masiis.shop.dao.mall.user;

import com.masiis.shop.dao.po.SfUserExtractApply;
import java.util.List;

public interface SfUserExtractApplyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfUserExtractApply record);

    SfUserExtractApply selectByPrimaryKey(Long id);

    List<SfUserExtractApply> selectAll();

    int updateByPrimaryKey(SfUserExtractApply record);
}