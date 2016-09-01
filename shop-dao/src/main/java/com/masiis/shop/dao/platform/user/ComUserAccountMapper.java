/*
 * ComUserAccountMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-17 Created
 */
package com.masiis.shop.dao.platform.user;

import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComUserAccount;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ComUserAccountMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ComUserAccount record);

    ComUserAccount selectByPrimaryKey(Long id);

    List<ComUserAccount> selectAll();

    ComUserAccount findByUserId(Long comUserId);


    int updateByPrimaryKey(ComUserAccount account);

    int updateByIdWithVersion(ComUserAccount account);

    /**
     * 统计销售额
     * @param userIds
     * @return
     */
    BigDecimal totalIncomeFeeByUserIds(List<Long> userIds);
}