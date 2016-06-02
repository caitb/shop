/*
 * PfUserCertificateMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-11 Created
 */
package com.masiis.shop.dao.platform.user;

import com.masiis.shop.dao.po.PfUserCertificate;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PfUserCertificateMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PfUserCertificate record);

    PfUserCertificate selectByPrimaryKey(Long id);

    List<PfUserCertificate> selectAll();

    int updateById(PfUserCertificate record);

    List<PfUserCertificate> selectSkuWeChatInfo(Long userId);

    PfUserCertificate selectByCode(String code);

    PfUserCertificate selectByUserSkuId(Integer pfuId);

    List<PfUserCertificate> selectByCondition(PfUserCertificate pfUserCertificate);

    List<PfUserCertificate> selectByUserId(Long userId);

    List<PfUserCertificate> selectByMap(Map<String, Object> conditionMap);

    PfUserCertificate selectByUserIdAndSkuId(@Param("userId")Long userId, @Param("skuId")Integer skuId);
}