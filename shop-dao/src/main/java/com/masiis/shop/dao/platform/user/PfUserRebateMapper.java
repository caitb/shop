/*
 * PfUserRebateMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-06-14 Created
 */
package com.masiis.shop.dao.platform.user;

import com.masiis.shop.dao.po.PfUserRebate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PfUserRebateMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PfUserRebate record);

    PfUserRebate selectByPrimaryKey(Long id);

    List<PfUserRebate> selectAll();

    int updateByPrimaryKey(PfUserRebate record);

    List<PfUserRebate> selectByUserId(Long userPid);

    List<PfUserRebate> selectByUserPId(Long userPid);

    PfUserRebate selectByUpgradeId(@Param("upgradeId") Long upgradeId);

    List<PfUserRebate> selectByUserPIdOrUserId(Long userId);
}