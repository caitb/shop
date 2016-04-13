/*
 * SfdistributionrecordMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-13 Created
 */
package com.masiis.shop.dao.mall.order;


import com.masiis.shop.dao.beans.order.SfDistributionPerson;
import com.masiis.shop.dao.beans.order.SfDistributionRecord;

import java.util.List;

public interface SfDistributionRecordMapper {

    SfDistributionRecord selectCountByUserId(Long userId);

    List<SfDistributionRecord> selectListByUserId(Long userId);

    List<SfDistributionPerson> selectListSfDistributionPersonByItemId(Long itemId);
}