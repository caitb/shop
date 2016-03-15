package com.masiis.shop.dao.platform.order;

import com.masiis.shop.dao.po.PfCorderPayment;

/**
 * @author muchaofeng
 * @date $date$ $time$
 */

public interface PfCordersPayment {
    PfCorderPayment selectByPrimaryKey(Long pfCorderId);
}
