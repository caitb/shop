/*
 * ComUserAccountMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-17 Created
 */
package com.masiis.shop.dao.platform.user;

import com.masiis.shop.dao.po.ComUserAccount;
import java.util.List;

public interface ComUserAccountMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ComUserAccount record);

    ComUserAccount selectByPrimaryKey(Long id);

    List<ComUserAccount> selectAll();

    int updateByPrimaryKey(ComUserAccount record);
}