/*
 * PfUserSkuMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package com.masiis.shop.dao.platform.user;

import com.masiis.shop.dao.beans.user.PfUserSkuCertificate;
import com.masiis.shop.dao.po.PfUserSku;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PfUserSkuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PfUserSku record);

    PfUserSku selectByPrimaryKey(Integer id);

    List<PfUserSku> selectAll();

    int updateByPrimaryKey(PfUserSku record);

    PfUserSku selectByUserIdAndSkuId(@Param("userId") Long userId, @Param("skuId") Integer skuId);


    List<PfUserSkuCertificate> getUserSkuList(@Param("searchParam") Map<String, Object> searchParam);

    Integer findLowerCount(Integer pid);

    PfUserSkuCertificate getUserSkuById(Integer id);

    PfUserSku selectByOrderId(Long bOrderId);

}