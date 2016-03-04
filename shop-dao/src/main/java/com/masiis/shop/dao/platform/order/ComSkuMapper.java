package com.masiis.shop.dao.platform.order;

import com.masiis.shop.dao.po.ComSku;
import org.apache.ibatis.annotations.Param;

/**
 * Created by 49134 on 2016/3/4.
 */
public interface ComSkuMapper {

    /**
     * 查找申请商品
     * @param skuId
     * @return
     */
    ComSku findBySkuId(@Param("id") Integer skuId);
}
