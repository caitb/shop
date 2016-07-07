/*
 * SfMessageContentMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-05 Created
 */
package com.masiis.shop.dao.mall.message;

import com.masiis.shop.dao.po.SfMessageContent;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SfMessageContentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfMessageContent record);

    SfMessageContent selectByPrimaryKey(Long id);

    List<SfMessageContent> selectAll();

    int updateByPrimaryKey(SfMessageContent record);

    /**
     * 根据userId和消息类型查询发出去的消息内容数量
     *
     * @param id
     * @return
     */
    Integer queryNumsFromUser(Long id, Integer type);
}