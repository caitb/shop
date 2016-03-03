/*
 * ComUserAddressMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package com.masiis.shop.dao.platform.user;

import com.masiis.shop.dao.po.ComUserAddress;

import java.util.List;

public interface ComUserAddressMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ComUserAddress record);

    ComUserAddress selectByPrimaryKey(Integer id);

    List<ComUserAddress> selectAll();

    int updateByPrimaryKey(ComUserAddress record);

    List<ComUserAddress> selectAllByComUserId(Long ComUserId);
}