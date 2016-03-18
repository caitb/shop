/*
 * ComUserExtractApplyMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-17 Created
 */
package com.masiis.shop.dao.platform.user;


import com.masiis.shop.dao.beans.extract.ExtractApply;
import com.masiis.shop.dao.po.ComUserExtractApply;

import java.util.List;

public interface ComUserExtractApplyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ComUserExtractApply record);

    ComUserExtractApply selectByPrimaryKey(Long id);

    List<ComUserExtractApply> selectAll();

    int updateByPrimaryKey(ComUserExtractApply record);

    List<ExtractApply> getExtractApplyList();
}