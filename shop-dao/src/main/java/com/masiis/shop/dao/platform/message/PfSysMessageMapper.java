/*
 * PfSysMessageMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-08-06 Created
 */
package com.masiis.shop.dao.platform.message;

import com.masiis.shop.dao.beans.message.PfMessageCenterDetail;
import com.masiis.shop.dao.po.PfMessageContent;
import com.masiis.shop.dao.po.PfSysMessage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PfSysMessageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PfSysMessage record);

    PfSysMessage selectByPrimaryKey(Long id);

    List<PfSysMessage> selectAll();

    int updateByPrimaryKey(PfSysMessage record);

    PfMessageCenterDetail querySysGeneralInfo(@Param("userId") Long userId);

    Integer selectNumsByUserIdAndType(@Param("userId") Long userId,
                                      @Param("mType") Integer mType);

    List<PfMessageContent> queryByUserIdAndTypeWithPaging(@Param("userId") Long userId,
                                                          @Param("mType") int mType,
                                                          @Param("start") int start,
                                                          @Param("pageSize") int pageSize);
}