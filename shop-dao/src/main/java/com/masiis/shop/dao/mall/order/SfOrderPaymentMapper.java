/*
 * SfOrderPaymentMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-08 Created
 */
package com.masiis.shop.dao.mall.order;

import com.masiis.shop.dao.po.SfOrderPayment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface SfOrderPaymentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfOrderPayment record);

    SfOrderPayment selectByPrimaryKey(Long id);

    List<SfOrderPayment> selectAll();

    int updateByPrimaryKey(SfOrderPayment record);

    List<SfOrderPayment> selectBySfOrderId(Long sfOrderId);

    SfOrderPayment selectBySerialNum(String paySerialNum);

    List<SfOrderPayment> selectByCondition(SfOrderPayment sfOrderPayment);

}