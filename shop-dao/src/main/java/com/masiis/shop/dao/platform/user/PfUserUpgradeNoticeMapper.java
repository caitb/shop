/*
 * PfUserUpgradeNoticeMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-06-14 Created
 */
package com.masiis.shop.dao.platform.user;

import com.masiis.shop.dao.po.PfUserUpgradeNotice;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PfUserUpgradeNoticeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PfUserUpgradeNotice record);

    PfUserUpgradeNotice selectByPrimaryKey(Long id);

    List<PfUserUpgradeNotice> selectAll();

    int updateByPrimaryKey(PfUserUpgradeNotice record);

    List<PfUserUpgradeNotice> selectByUserId(Long userId);

    List<PfUserUpgradeNotice> selectByUserPId(Long userPid);

    List<PfUserUpgradeNotice> selectByMap(Map<String,Object> conditionMap);
}