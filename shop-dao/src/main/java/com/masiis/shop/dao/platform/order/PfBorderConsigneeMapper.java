/*
 * PfBorderConsigneeMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package com.masiis.shop.dao.platform.order;

import com.masiis.shop.dao.po.PfBorderConsignee;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PfBorderConsigneeMapper {

    PfBorderConsignee selectById(Long id);

    List<PfBorderConsignee> selectByCondition(PfBorderConsignee pfBorderConsignee);

    int insert(PfBorderConsignee pfBorderConsignee);

    void updateById(PfBorderConsignee pfBorderConsignee);

    void deleteById(Long id);

    PfBorderConsignee selectByBorderId(Long borderId);
}