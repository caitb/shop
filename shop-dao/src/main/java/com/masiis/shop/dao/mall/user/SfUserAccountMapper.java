/*
 * SfUserAccountMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-08 Created
 */
package com.masiis.shop.dao.mall.user;

import com.masiis.shop.dao.po.SfUserAccount;
import java.util.List;

public interface SfUserAccountMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfUserAccount record);

    SfUserAccount selectByPrimaryKey(Long id);

    List<SfUserAccount> selectAll();

    int updateByPrimaryKey(SfUserAccount record);

    SfUserAccount selectByUserId(Long userId);
}