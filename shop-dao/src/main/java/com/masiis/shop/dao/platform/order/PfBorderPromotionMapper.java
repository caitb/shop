/*
 * PfBorderPromotionMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-09-06 Created
 */
package com.masiis.shop.dao.platform.order;

import com.masiis.shop.dao.po.PfBorderPromotion;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PfBorderPromotionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PfBorderPromotion record);

    PfBorderPromotion selectByPrimaryKey(Long id);

    PfBorderPromotion getBorderPromotionsByBorderIdAndIsTake(@Param("pfBorderId") Long pfBorderId, @Param("isTake") Integer isTake);

    PfBorderPromotion getBorderPromotionsByBorderIdAndIsSend(@Param("pfBorderId") Long pfBorderId, @Param("isSend") Integer isSend);

    List<PfBorderPromotion> getExpirePromotionsByIsSendAndQuantityAndTime(@Param("isSend") Integer isSend, @Param("quantity") Integer quantity, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    List<PfBorderPromotion> selectAll();

    int updateByPrimaryKey(PfBorderPromotion record);

    List<PfBorderPromotion> selectByPfBorderId(@Param("pfBorderId") Long pfBorderId);
}