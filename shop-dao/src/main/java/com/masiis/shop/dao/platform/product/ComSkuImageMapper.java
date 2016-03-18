/*
 * ComSkuImageMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-04 Created
 */
package com.masiis.shop.dao.platform.product;

import com.masiis.shop.dao.po.ComSkuImage;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ComSkuImageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ComSkuImage record);

    ComSkuImage selectByPrimaryKey(Integer id);

    List<ComSkuImage> selectAll();

    int updateByPrimaryKey(ComSkuImage record);

    ComSkuImage selectDefaultImgBySkuId(Integer skuId);

    List<ComSkuImage> selectBySkuId(Integer skuId);
}