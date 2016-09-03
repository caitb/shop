/*
 * ComUserBakMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-09-02 Created
 */
package com.masiis.shop.dao.platform.user;

import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComUserBak;
import java.util.List;

public interface ComUserBakMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ComUser record);

    ComUserBak selectByPrimaryKey(Long id);

    List<ComUserBak> selectAll();

    int updateByPrimaryKey(ComUserBak record);
}