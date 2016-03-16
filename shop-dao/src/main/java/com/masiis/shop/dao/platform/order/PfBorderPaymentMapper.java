/*
 * PfBorderPaymentMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package com.masiis.shop.dao.platform.order;

import com.masiis.shop.dao.po.PfBorderPayment;
import com.masiis.shop.dao.po.PfCorderPayment;

import java.util.List;

public interface PfBorderPaymentMapper {

    PfBorderPayment selectById(Long id);

    PfBorderPayment selectByBorderId(Long borderId);

    List<PfBorderPayment> selectByCondition(PfBorderPayment pfBorderPayment);

    int insert(PfBorderPayment pfBorderPayment);

    int updateById(PfBorderPayment pfBorderPayment);

    int deleteById(Long id);

    PfBorderPayment selectBySerialNum(String paySerialNum);
	PfBorderPayment selectByPaySerialNumAndAmount(@Param("paySerialNum") String paySerialNum, @Param("amount") BigDecimal amount);
}