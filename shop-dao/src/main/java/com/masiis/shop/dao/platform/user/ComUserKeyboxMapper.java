/*
 * ComUserKeyboxMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-27 Created
 */
package com.masiis.shop.dao.platform.user;

import com.masiis.shop.dao.po.ComUserKeybox;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComUserKeyboxMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ComUserKeybox record);

    ComUserKeybox selectByPrimaryKey(Long id);

    List<ComUserKeybox> selectAll();

    int updateByPrimaryKey(ComUserKeybox record);

    ComUserKeybox getByComUserId(Long userId);

    ComUserKeybox selectByToken(String token);
}