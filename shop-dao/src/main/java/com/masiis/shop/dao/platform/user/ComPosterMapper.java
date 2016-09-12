/*
 * ComPosterMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-18 Created
 */
package com.masiis.shop.dao.platform.user;


import com.masiis.shop.dao.po.ComPoster;

import java.util.List;
import java.util.Map;

public interface ComPosterMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ComPoster record);

    ComPoster selectByPrimaryKey(Long id);

    List<ComPoster> selectAll();

    int updateByPrimaryKey(ComPoster record);

    ComPoster selectByCondition(ComPoster comPoster);

    void delete(Integer id);

    List<Map<String,Object>> posterList(Map<String, Object> map);
}