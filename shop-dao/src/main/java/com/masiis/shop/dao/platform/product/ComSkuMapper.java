/*
 * ComSkuMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-04 Created
 */
package com.masiis.shop.dao.platform.product;

import com.masiis.shop.dao.po.ComSku;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ComSkuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ComSku record);

    ComSku selectByPrimaryKey(Integer id);

    List<ComSku> selectAll();

    int updateByPrimaryKey(ComSku record);

    /**
     * 查找申请商品
     * @param skuId
     * @return
     */
    ComSku findBySkuId(@Param("skuId") Integer skuId);
}