/*
 * SfOrderPaymentMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao.po;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface SfOrderPaymentMapper {
    int countByExample(SfOrderPaymentExample example);

    int deleteByExample(SfOrderPaymentExample example);

    @Insert({
        "insert into sf_order_payment (id)",
        "values (#{id,jdbcType=BIGINT})"
    })
    int insert(SfOrderPayment record);

    int insertSelective(SfOrderPayment record);

    List<SfOrderPayment> selectByExample(SfOrderPaymentExample example);

    int updateByExampleSelective(@Param("record") SfOrderPayment record, @Param("example") SfOrderPaymentExample example);

    int updateByExample(@Param("record") SfOrderPayment record, @Param("example") SfOrderPaymentExample example);
}