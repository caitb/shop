/*
 * SfUserTurnTableItemMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-29 Created
 */
package com.masiis.shop.dao.mall.promotion;

import com.masiis.shop.dao.po.SfUserTurnTableItem;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SfUserTurnTableItemMapper {
    int deleteByPrimaryKey(@Param("id") Long id, @Param("turnTableId") Integer turnTableId, @Param("userTurnTableId") Long userTurnTableId, @Param("turnTableRuleId") Integer turnTableRuleId, @Param("type") Integer type, @Param("remark") String remark, @Param("createTime") Date createTime);

    int insert(SfUserTurnTableItem record);

    SfUserTurnTableItem selectByPrimaryKey(@Param("id") Long id, @Param("turnTableId") Integer turnTableId, @Param("userTurnTableId") Long userTurnTableId, @Param("turnTableRuleId") Integer turnTableRuleId, @Param("type") Integer type, @Param("remark") String remark, @Param("createTime") Date createTime);

    List<SfUserTurnTableItem> selectAll();

    int updateByPrimaryKey(SfUserTurnTableItem record);
}