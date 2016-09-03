package com.masiis.shop.dao.platform.order;

import com.masiis.shop.dao.po.PfCorderPayment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author muchaofeng
 * @date $date$ $time$
 */
@Repository
public interface PfCorderPaymentMapper {

    PfCorderPayment selectById(Long id);

    List<PfCorderPayment> selectByCorderId(Long corderId);

    List<PfCorderPayment> selectByCondition(PfCorderPayment pfCorderPayment);

    void insert(PfCorderPayment pfCorderPayment);

    void updateById(PfCorderPayment pfCorderPayment);

    void deleteById(Long id);

    PfCorderPayment selectBySerialNum(String paySerialNum);
}
