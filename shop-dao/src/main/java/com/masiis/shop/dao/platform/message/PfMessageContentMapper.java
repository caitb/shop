/*
 * PfMessageContentMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-12 Created
 */
package com.masiis.shop.dao.platform.message;

import com.masiis.shop.dao.po.PfMessageContent;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PfMessageContentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PfMessageContent record);

    PfMessageContent selectByPrimaryKey(Long id);

    List<PfMessageContent> selectAll();

    int updateByPrimaryKey(PfMessageContent record);
}