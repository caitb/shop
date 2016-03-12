/*
 * PfUserCertificateMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-11 Created
 */
package com.masiis.shop.dao.platform.user;

import com.masiis.shop.dao.po.PfUserCertificate;

import java.util.List;

public interface PfUserCertificateMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PfUserCertificate record);

    PfUserCertificate selectByPrimaryKey(Long id);

    List<PfUserCertificate> selectAll();

    int updateByPrimaryKey(PfUserCertificate record);
}