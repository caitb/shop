/*
 * PfUserAgentApplicationMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-04 Created
 */
package com.masiis.shop.dao.platform.user;

import com.masiis.shop.dao.po.PfUserAgentApplication;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PfUserAgentApplicationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PfUserAgentApplication record);

    PfUserAgentApplication selectByPrimaryKey(Long id);

    List<PfUserAgentApplication> selectAll();

    int updateByPrimaryKey(PfUserAgentApplication record);

    PfUserAgentApplication selectByPhone(@Param("mobile") String mobile);
}