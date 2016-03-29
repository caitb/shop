/*
 * ComUserAccountRecordMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-29 Created
 */
package com.masiis.shop.dao.platform.user;

import com.masiis.shop.dao.po.ComUserAccountRecord;
import java.util.List;

public interface ComUserAccountRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ComUserAccountRecord record);

    ComUserAccountRecord selectByPrimaryKey(Long id);

    List<ComUserAccountRecord> selectAll();

    int updateByPrimaryKey(ComUserAccountRecord record);
}