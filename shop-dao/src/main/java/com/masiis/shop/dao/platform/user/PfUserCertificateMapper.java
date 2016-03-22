/*
 * PfUserCertificateMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-11 Created
 */
package com.masiis.shop.dao.platform.user;

import com.masiis.shop.dao.po.PfUserCertificate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PfUserCertificateMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PfUserCertificate record);

    PfUserCertificate selectByPrimaryKey(Long id);

    List<PfUserCertificate> selectAll();

    int updateById(PfUserCertificate record);

    PfUserCertificate selectByUserId(Long userId);

    List<PfUserCertificate> selectByCode(Long userId);

    PfUserCertificate selectByUserSkuId(Integer pfuId);

    List<PfUserCertificate> selectByCondition(PfUserCertificate pfUserCertificate);
}