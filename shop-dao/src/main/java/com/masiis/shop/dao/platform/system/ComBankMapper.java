/*
 * ComBankMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-19 Created
 */
package com.masiis.shop.dao.platform.system;

import com.masiis.shop.dao.po.ComBank;
import java.util.List;

public interface ComBankMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ComBank record);

    ComBank selectByPrimaryKey(Integer id);

    List<ComBank> selectAll();

    int updateByPrimaryKey(ComBank record);
}