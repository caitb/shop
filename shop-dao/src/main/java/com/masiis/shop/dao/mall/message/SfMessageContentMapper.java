/*
 * SfMessageContentMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-05 Created
 */
package com.masiis.shop.dao.mall.message;

import com.masiis.shop.dao.po.SfMessageContent;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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
     * @param params
     * @return
     */
    Integer queryNumsFromUser(@Param("params")Map<String, Object> params);

    List<SfMessageContent> selectByFromUserAndType(@Param("params")Map<String, Object> params);

    /**
     * 查询店铺id和对应的未读消息数量
     * @param userId
     */
    List<Map<String, Long>> selectUnreadNumsAndFromByUser(Long userId);

    /**
     * 查询店铺信息和最新的未读消息
     * @param msgId
     */
    Map<String, String> selectShopInfoAndFirMsgByMsgId(Long msgId);
}