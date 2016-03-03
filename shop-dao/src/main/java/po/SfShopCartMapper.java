/*
 * SfShopCartMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package po;

import com.masiis.shop.dao.SfShopCart;
import org.apache.ibatis.annotations.Insert;

public interface SfShopCartMapper {
    @Insert({
        "insert into sf_shop_cart (id)",
        "values (#{id,jdbcType=BIGINT})"
    })
    int insert(SfShopCart record);

    int insertSelective(SfShopCart record);
}