/*
 * PfMessageSrRelationMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-07-12 Created
 */
package com.masiis.shop.dao.platform.message;

import com.masiis.shop.dao.beans.message.PfMessageCenterDetail;
import com.masiis.shop.dao.po.PfMessageContent;
import com.masiis.shop.dao.po.PfMessageSrRelation;
import com.sun.org.glassfish.external.statistics.annotations.Reset;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
@Repository
public interface PfMessageSrRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PfMessageSrRelation record);

    PfMessageSrRelation selectByPrimaryKey(Long id);

    List<PfMessageSrRelation> selectAll();

    int updateByPrimaryKey(PfMessageSrRelation record);

    /**
     * 查询收到的消息来自多少人(消息来源人的总数)
     *
     * @param userId
     * @return
     */
    Integer querySrRelationNumsToUser(@Param("userId") Long userId,
                                      @Param("mType") Integer mType);

    /**
     * 按消息接受者来查询
     *
     * @param userId
     * @param mType
     * @param start
     * @param pageSize
     * @return
     */
    List queryByToUserWithPaging(@Param("userId") Long userId,
                                 @Param("mType") Integer mType,
                                 @Param("start") Integer start,
                                 @Param("pageSize") Integer pageSize);

    /**
     * 根据消息来源用户和当前用户查询消息总条数
     *
     * @param userId
     * @param fUserId
     * @param mType
     * @return
     */
    Integer queryNumsByFromUseAndToUserAndType(@Param("userId") Long userId,
                                               @Param("fUserId") Long fUserId,
                                               @Param("mType") Integer mType);

    /**
     * 根据消息来源和当前用户查询消息详情（带分页）
     *
     * @param userId
     * @param fUserId
     * @param mType
     * @param start
     * @param pageSize
     * @return
     */
    List<PfMessageContent> queryDetailByFromUserAndToUserWithPaging(@Param("userId") Long userId,
                                                                    @Param("fUserId") Long fUserId,
                                                                    @Param("mType") Integer mType,
                                                                    @Param("start") Integer start,
                                                                    @Param("pageSize") Integer pageSize);

    /**
     * 根据来源用户和接收用户更新消息为已查看
     *
     * @param fUserId
     * @param tUserId
     * @return
     */
    Integer updateRelationIsSeeByFromUserAndToUser(@Param("fUserId") Long fUserId,
                                                   @Param("tUserId") Long tUserId);
}