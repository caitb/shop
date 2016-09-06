/*
 * ComUserBlacklistMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-09-06 Created
 */
package com.masiis.shop.dao.platform.user;


import com.masiis.shop.dao.po.ComUserBlacklist;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ComUserBlacklistMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ComUserBlacklist record);

    ComUserBlacklist selectByPrimaryKey(Long id);

    List<ComUserBlacklist> selectAll();

    int updateByPrimaryKey(ComUserBlacklist record);

    ComUserBlacklist selectByMobile(String mobile);
}