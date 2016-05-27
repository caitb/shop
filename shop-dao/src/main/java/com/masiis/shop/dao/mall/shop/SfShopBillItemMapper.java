/*
 * SfShopBillItemMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-05-26 Created
 */
package com.masiis.shop.dao.mall.shop;

import com.masiis.shop.dao.po.SfShopBillItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
@Repository
public interface SfShopBillItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfShopBillItem record);

    SfShopBillItem selectByPrimaryKey(Long id);

    List<SfShopBillItem> selectAll();

    int updateByPrimaryKey(SfShopBillItem record);

    /**
     * 根据店铺id和时间来查询店铺账单子项
     *
     * @param shopId
     * @param start
     * @param end
     * @return
     */
    List<SfShopBillItem> selectByShopIdAndDate(@Param("shopId") Long shopId,
                                               @Param("start") Date start,
                                               @Param("end") Date end);
}