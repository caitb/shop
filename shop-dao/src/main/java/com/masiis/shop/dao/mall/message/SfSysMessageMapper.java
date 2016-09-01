/*
 * SfSysMessageMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-08-06 Created
 */
package com.masiis.shop.dao.mall.message;

import com.masiis.shop.dao.beans.message.PfMessageCenterDetail;
import com.masiis.shop.dao.po.SfMessageContent;
import com.masiis.shop.dao.po.SfSysMessage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SfSysMessageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfSysMessage record);

    SfSysMessage selectByPrimaryKey(Long id);

    List<SfSysMessage> selectAll();

    int updateByPrimaryKey(SfSysMessage record);

    PfMessageCenterDetail querySysGeneralInfo(@Param("userId") Long userId);

    Integer selectNumsByUserIdAndType(@Param("userId") Long userId,
                                      @Param("mType") int mType);

    List<SfMessageContent> queryByUserIdAndTypeWithPaging(@Param("userId") Long userId,
                                                          @Param("mType") int mType,
                                                          @Param("start") int start,
                                                          @Param("pageSize") int pageSize);
}