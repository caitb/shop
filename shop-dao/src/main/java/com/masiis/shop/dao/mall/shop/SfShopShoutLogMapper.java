/*
 * SfShopShoutLogMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-08 Created
 */
package com.masiis.shop.dao.mall.shop;

import com.masiis.shop.dao.po.SfShopShoutLog;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
@Repository
public interface SfShopShoutLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfShopShoutLog record);

    SfShopShoutLog selectByPrimaryKey(Long id);

    List<SfShopShoutLog> selectAll();

    int updateByPrimaryKey(SfShopShoutLog record);

    SfShopShoutLog selectByCondition(Map<String, Object> con);

    SfShopShoutLog selectByMap(Map<String, Object> map);
}