package com.masiis.shop.dao.platform.order;

import com.masiis.shop.dao.po.PfCorderPayment;

import java.util.List;

/**
 * @author muchaofeng
 * @date $date$ $time$
 */

public interface PfCorderPaymentMapper {

    PfCorderPayment selectById(Long id);

    PfCorderPayment selectByCorderId(Long corderId);

    List<PfCorderPayment> selectByCondition(PfCorderPayment pfCorderPayment);

    void insert(PfCorderPayment pfCorderPayment);

    void updateById(PfCorderPayment pfCorderPayment);

    void deleteById(Long id);

    PfCorderPayment selectBySerialNum(String paySerialNum);
}
