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
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SfDistributionRecordMapper {

    SfDistributionRecord selectCountByUserId(Long userId);

    List<SfDistributionRecord> selectListByUserIdLimt(@Param("userid") Long userid,
                                                      @Param("start") Date start,
                                                      @Param("end") Date end);

    Integer selectCountByUserIdLimit(@Param("userid") Long userid,
                                     @Param("start") Date start,
                                     @Param("end") Date end);

    List<SfDistributionPerson> selectListSfDistributionPersonByItemId(Long itemId);
}