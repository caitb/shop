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



    Integer queryShopNums(Long userId);


    List<Map<String, String>> selectShopInfoAndFirstMsg(@Param("userId") Long userId, @Param("start") Integer start, @Param("size") Integer size);

    void updateRelationIsSeeByFromUserAndToUser(@Param("fromUser")Long fromUser, @Param("toUser")Long toUser);

    Integer queryNumsFromUserAndToUser(@Param("fromUser") Long fromUser, @Param("toUser") Long toUser);

    List<SfMessageContent> queryDetailByFromUserAndToUserWithPaging(@Param("userId")Long userId, @Param("fUserId")Long fUserId, @Param("start")Integer start, @Param("pageSize")Integer pageSize);
}