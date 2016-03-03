/*
 * SfOrderItemMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package po;

import com.masiis.shop.dao.SfOrderItem;
import org.apache.ibatis.annotations.Insert;

public interface SfOrderItemMapper {
    @Insert({
        "insert into sf_order_item (id)",
        "values (#{id,jdbcType=BIGINT})"
    })
    int insert(SfOrderItem record);

    int insertSelective(SfOrderItem record);
}