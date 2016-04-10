/*
 * SfUserExtractPaymentMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-10 Created
 */
package com.masiis.shop.dao.mall.user;

import com.masiis.shop.dao.po.SfUserExtractPayment;
import java.util.List;

public interface SfUserExtractPaymentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfUserExtractPayment record);

    SfUserExtractPayment selectByPrimaryKey(Long id);

    List<SfUserExtractPayment> selectAll();

    int updateByPrimaryKey(SfUserExtractPayment record);
}