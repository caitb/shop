/*
 * SfOrderConsigneeMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package po;

import com.masiis.shop.dao.SfOrderConsignee;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;

public interface SfOrderConsigneeMapper {
    @Delete({
        "delete from sf_order_consignee",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into sf_order_consignee (id)",
        "values (#{id,jdbcType=BIGINT})"
    })
    int insert(SfOrderConsignee record);

    int insertSelective(SfOrderConsignee record);
}