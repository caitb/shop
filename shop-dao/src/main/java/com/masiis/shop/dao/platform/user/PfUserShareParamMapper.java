/*
 * PfUserShareParamMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-06-14 Created
 */
package com.masiis.shop.dao.platform.user;

import com.masiis.shop.dao.po.PfUserShareParam;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PfUserShareParamMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PfUserShareParam record);

    PfUserShareParam selectByPrimaryKey(Long id);

    List<PfUserShareParam> selectAll();

    int updateByPrimaryKey(PfUserShareParam record);
}