/*
 * PfUserCertificateHistoryMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-06-17 Created
 */
package com.masiis.shop.dao.platform.user;

import com.masiis.shop.dao.po.PfUserCertificateHistory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PfUserCertificateHistoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PfUserCertificateHistory record);

    PfUserCertificateHistory selectByPrimaryKey(Long id);

    List<PfUserCertificateHistory> selectAll();

    int updateByPrimaryKey(PfUserCertificateHistory record);
}