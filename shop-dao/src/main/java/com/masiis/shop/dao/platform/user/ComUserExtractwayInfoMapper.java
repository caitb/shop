/*
 * ComUserExtractwayInfoMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-17 Created
 */
package com.masiis.shop.dao.platform.user;

import com.masiis.shop.dao.po.ComUserExtractwayInfo;
import java.util.List;

public interface ComUserExtractwayInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ComUserExtractwayInfo record);

    ComUserExtractwayInfo selectByPrimaryKey(Long id);

    List<ComUserExtractwayInfo> selectAll();

    int updateByPrimaryKey(ComUserExtractwayInfo record);

    ComUserExtractwayInfo selectByBankcardAndCardownername(ComUserExtractwayInfo record);
}