/*
 * PfUserBillItemMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-22 Created
 */
package com.masiis.shop.dao.platform.user;

import com.masiis.shop.dao.po.PfUserBillItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PfUserBillItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PfUserBillItem record);

    PfUserBillItem selectByPrimaryKey(Long id);

    List<PfUserBillItem> selectAll();

    int updateByPrimaryKey(PfUserBillItem record);
}