/*
 * SfUserAccountRecordMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-10 Created
 */
package com.masiis.shop.dao.mall.user;

import com.masiis.shop.dao.po.SfUserAccountRecord;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SfUserAccountRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfUserAccountRecord record);

    SfUserAccountRecord selectByPrimaryKey(Long id);

    List<SfUserAccountRecord> selectAll();

    int updateByPrimaryKey(SfUserAccountRecord record);
}