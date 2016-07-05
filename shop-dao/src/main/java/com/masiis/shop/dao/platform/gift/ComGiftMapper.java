/*
 * ComGiftMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-04 Created
 */
package com.masiis.shop.dao.platform.gift;

import com.masiis.shop.dao.po.ComGift;

import java.util.List;


public interface ComGiftMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ComGift record);

    ComGift selectByPrimaryKey(Integer id);

    List<ComGift> selectAll();

    int updateByPrimaryKey(ComGift record);
}