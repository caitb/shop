/*
 * PfBorderPaymentMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package com.masiis.shop.dao.platform.order;

import com.masiis.shop.dao.po.PfBorderPayment;
import java.util.List;

public interface PfBorderPaymentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PfBorderPayment record);

    PfBorderPayment selectByPrimaryKey(Long id);

    List<PfBorderPayment> selectAll();

    int updateByPrimaryKey(PfBorderPayment record);
}