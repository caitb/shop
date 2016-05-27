/*
 * SfShopBillMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-05-26 Created
 */
package com.masiis.shop.dao.mall.shop;

import com.masiis.shop.dao.po.SfShopBill;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public interface SfShopBillMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfShopBill record);

    SfShopBill selectByPrimaryKey(Long id);

    List<SfShopBill> selectAll();

    int updateByPrimaryKey(SfShopBill record);

    Long selectShopBillNumsByDateAndShopId(@Param("start") Date start,
                                           @Param("end") Date end,
                                           @Param("shopId") Long shopId);
}