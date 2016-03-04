/*
 * ComSkuImageMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-04 Created
 */
package com.masiis.shop.dao.platform.product;

import com.masiis.shop.dao.po.ComSkuImage;
import java.util.List;

public interface ComSkuImageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ComSkuImage record);

    ComSkuImage selectByPrimaryKey(Integer id);

    List<ComSkuImage> selectAll();

    int updateByPrimaryKey(ComSkuImage record);

    ComSkuImage selectDefaultImgBySkuId(Integer id);
}