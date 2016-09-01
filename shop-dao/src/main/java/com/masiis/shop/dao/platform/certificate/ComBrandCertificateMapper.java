/*
 * ComBrandCertificateMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-08-18 Created
 */
package com.masiis.shop.dao.platform.certificate;

import com.masiis.shop.dao.po.ComBrandCertificate;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ComBrandCertificateMapper {
    int deleteByPrimaryKey(@Param("id") Integer id);

    int insert(ComBrandCertificate record);

    ComBrandCertificate selectByPrimaryKey(@Param("id") Integer id);

    ComBrandCertificate getBrandCertificateByBrandIdAndLevelId(@Param("brandId") Integer brandId,  @Param("agentLevelId") Integer agentLevelId);

    List<ComBrandCertificate> selectAll();

    int updateByPrimaryKey(ComBrandCertificate record);
}