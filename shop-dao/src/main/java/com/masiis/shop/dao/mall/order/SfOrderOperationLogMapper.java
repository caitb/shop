/*
 * SfOrderOperationLogMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-08 Created
 */
package com.masiis.shop.dao.mall.order;

import com.masiis.shop.dao.po.SfOrderOperationLog;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
@Repository
public interface SfOrderOperationLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfOrderOperationLog record);

    SfOrderOperationLog selectByPrimaryKey(Long id);

    SfOrderOperationLog getOrdOperLogByOrderId(Long sfOrderId);

    List<SfOrderOperationLog> selectAll();

    int updateByPrimaryKey(SfOrderOperationLog record);
}