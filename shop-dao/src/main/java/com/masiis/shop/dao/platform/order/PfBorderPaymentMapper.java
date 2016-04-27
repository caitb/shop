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
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PfBorderPaymentMapper {

    PfBorderPayment selectById(Long id);

    List<PfBorderPayment> selectByBorderId(Long borderId);

    List<PfBorderPayment> selectByCondition(PfBorderPayment pfBorderPayment);

    int insert(PfBorderPayment pfBorderPayment);

    int updateById(PfBorderPayment pfBorderPayment);

    int deleteById(Long id);

    PfBorderPayment selectBySerialNum(String paySerialNum);
	PfBorderPayment selectByPaySerialNumAndAmount(@Param("paySerialNum") String paySerialNum, @Param("amount") BigDecimal amount);

    PfBorderPayment selectByOrderIdAndPayTypeIdAndIsEnabled(@Param("pfBorderId") Long pfBorderId, @Param("payTypeId") Integer payTypeId,@Param("isEnabled") Integer isEnabled);

    /**
     * 查找线下支付记录
     * @param bOrderId
     * @return
     */
    PfBorderPayment selectOfflinePayByBOrderId(Long bOrderId);


}