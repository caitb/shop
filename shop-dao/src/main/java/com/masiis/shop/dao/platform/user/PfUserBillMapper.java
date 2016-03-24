/*
 * PfUserBillMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-22 Created
 */
package com.masiis.shop.dao.platform.user;

import com.masiis.shop.dao.po.PfUserBill;
import java.util.List;

public interface PfUserBillMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PfUserBill record);

    PfUserBill selectByPrimaryKey(Long id);

    List<PfUserBill> selectAll();

    int updateByPrimaryKey(PfUserBill record);

    List<PfUserBill> selectByUserId(Long userId);

    List<PfUserBill> selectByUserIdLimitPage(Long userId,String balanceDate);
}