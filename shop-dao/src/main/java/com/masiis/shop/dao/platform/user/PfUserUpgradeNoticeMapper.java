/*
 * PfUserUpgradeNoticeMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-06-14 Created
 */
package com.masiis.shop.dao.platform.user;

import com.masiis.shop.dao.po.PfUserUpgradeNotice;
import java.util.List;

public interface PfUserUpgradeNoticeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PfUserUpgradeNotice record);

    PfUserUpgradeNotice selectByPrimaryKey(Long id);

    List<PfUserUpgradeNotice> selectAll();

    int updateByPrimaryKey(PfUserUpgradeNotice record);

    List<PfUserUpgradeNotice> selectByUserId(Long userId);

    List<PfUserUpgradeNotice> selectByUserPId(Long userPid);
}