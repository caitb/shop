/*
 * SfOrderPaymentMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package po;

import com.masiis.shop.dao.SfOrderPayment;
import org.apache.ibatis.annotations.Insert;

public interface SfOrderPaymentMapper {
    @Insert({
        "insert into sf_order_payment (id)",
        "values (#{id,jdbcType=BIGINT})"
    })
    int insert(SfOrderPayment record);

    int insertSelective(SfOrderPayment record);
}