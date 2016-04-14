/*
 * PfSkuAgentMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-04 Created
 */
package com.masiis.shop.dao.platform.product;

import com.masiis.shop.dao.po.PfSkuAgent;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Map;

@Repository
public interface PfSkuAgentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PfSkuAgent record);
    Double selectMaxBail(Integer skuId);
    Double selectMinBail(Integer skuId);

    PfSkuAgent selectByPrimaryKey(Integer id);

    List<PfSkuAgent> selectAll();

    int updateByPrimaryKey(PfSkuAgent record);

    List<PfSkuAgent> selectAllBySkuId(Integer sku_id);

    PfSkuAgent selectBySkuIdAndLevelId(@Param("skuId") Integer skuId, @Param("levelId") Integer levelId);

    List<PfSkuAgent> selectBySkuId(Integer skuId);

    void updateById(PfSkuAgent pfSkuAgent);

    /*获取用户商品等级标志*/
    List<PfSkuAgent> getSkuLevelIconByUserId(Long userId);
}