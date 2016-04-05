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
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PfUserSkuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PfUserSku record);

    PfUserSku selectByPrimaryKey(Integer id);

    List<PfUserSku> selectAll();

    int updateByPrimaryKey(PfUserSku record);

    PfUserSku selectByUserIdAndSkuId(@Param("userId") Long userId, @Param("skuId") Integer skuId);


    List<PfUserSkuCertificate> getUserSkuList(@Param("searchParam") Map<String, Object> searchParam);

    Integer findLowerCount(Integer pid);

    List<PfUserSkuCertificate> getUserSkuListById(@Param("id") Integer id);

    PfUserSku selectByOrderIdAndUserIdAndSkuId(@Param("bOrderId") Long bOrderId, @Param("userId") Long userId, @Param("skuId") Integer skuId);

    /**
     * 根据条件查询记录
     *
     * @param pfUserSku
     * @return
     */
    List<PfUserSku> selectByCondition(PfUserSku pfUserSku);

    /**
     * 统计团队人数
     * @param sPIds
     * @return
     */
    Map<String, String> countChild(String sPIds);

}