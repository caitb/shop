/*
 * PfMessageContentMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-12 Created
 */
package com.masiis.shop.dao.platform.message;

import com.masiis.shop.dao.po.PfMessageContent;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PfMessageContentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PfMessageContent record);

    PfMessageContent selectByPrimaryKey(Long id);

    List<PfMessageContent> selectAll();

    int updateByPrimaryKey(PfMessageContent record);

    /**
     * 查看发出的消息内容
     *
     * @param userId
     * @return
     */
    List<PfMessageContent> queryByUserId(@Param("userId") Long userId,
                                         @Param("type") Integer type);

    /**
     * 查询发出的最新一条消息
     *
     * @param userId
     * @param type
     * @return
     */
    PfMessageContent queryLatestByUserIdAndType(@Param("userId") Long userId,
                                                @Param("type") Integer type);

    /**
     * 根据消息创建者和类型查询消息（带分页）
     *
     * @param userId
     * @param type
     * @param start
     * @param pageSize
     * @return
     */
    List<PfMessageContent> queryByUserIdAndTypeWithPaging(@Param("userId") Long userId,
                                                          @Param("type") Integer type,
                                                          @Param("start") Integer start,
                                                          @Param("pageSize") Integer pageSize);
}