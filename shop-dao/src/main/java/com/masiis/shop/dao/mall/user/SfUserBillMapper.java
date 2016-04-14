/*
 * SfUserBillMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-12 Created
 */
package com.masiis.shop.dao.mall.user;

import com.masiis.shop.dao.po.SfUserBill;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SfUserBillMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfUserBill record);

    SfUserBill selectByPrimaryKey(Long id);

    List<SfUserBill> selectAll();

    int updateByPrimaryKey(SfUserBill record);
}