/*
 * ComUserMaterielApplicationMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-04 Created
 */
package com.masiis.shop.dao.platform.material;


import com.masiis.shop.dao.po.ComUserMaterielApplication;

import java.util.List;

public interface ComUserMaterielApplicationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ComUserMaterielApplication record);

    ComUserMaterielApplication selectByPrimaryKey(Long id);

    List<ComUserMaterielApplication> selectAll();

    int updateByPrimaryKey(ComUserMaterielApplication record);
}