/*
 * SfUserRelationMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-08 Created
 */
package com.masiis.shop.dao.mall.user;

import com.masiis.shop.dao.beans.user.SfSpokesAndFansInfo;
import com.masiis.shop.dao.po.SfUserRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SfUserRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfUserRelation record);

    SfUserRelation selectByPrimaryKey(Long id);

    List<SfUserRelation> selectAll();

    int updateByPrimaryKey(SfUserRelation record);

    List<SfUserRelation> getSfUserRelationByUserId(Long userId);

    List<SfUserRelation> getThreeDistributionList(Long userPid);

    SfUserRelation selectSfUserRelationByUserIdAndShopId(@Param("userId") Long userId,
                                                         @Param("shopId") Long shopId);

    /**
     * 查询粉丝
     * @param userPid   用户id
     * @param userLevel 粉丝级别
     * @param shopId    小铺id
     * @param sopkenMan 代言人
     * @return
     */
    List<SfSpokesAndFansInfo> selectFansPageView(@Param("userId") Long userPid,
                                                 @Param("userLevel") Integer userLevel,
                                                 @Param("shopId") Long shopId,
                                                 @Param("sopkenMan") Integer sopkenMan);


    /**
     * 通过shopId查询所有代言人
     * @param shopId    小铺id
     * @param spokesMan 是否已代言可以为null
     * @return
     */
    List<SfSpokesAndFansInfo> selectAllSpokesManByShopId(@Param("shopId") Long shopId, @Param("spokesMan") Integer spokesMan);

    /**
     * 通过shopId查询所有代言人数量
     * @param shopId    小铺id
     * @param spokesMan 是否已代言可以为null
     * @return
     */
    Integer selectAllSopkesManCountByShopId(@Param("shopId") Long shopId, @Param("spokesMan") Integer spokesMan);

    /**
     * 通过treecode获取粉丝数量
     * 该方法查询的是所有的粉丝包括自己
     * @param treeCode treeCode
     * @return Integer
     */
    Map<String, Number> selectFansNum(@Param("treeCode") String treeCode,
                                      @Param("shopId") Long shopId);

    /**
     * 查询代言人数量
     * @param treeCode  treeCode
     * @return  map
     */
    Map<String, Number> selectSpokesManNum(@Param("treeCode") String treeCode,
                                           @Param("shopId") Long shopId);

    /**
     * 修改树形编码
     *
     * @param id       主键id
     * @param treeCode 属性编码
     * @return
     */
    int updateTreeCodeById(@Param("id") Long id, @Param("treeCode") String treeCode);

    /**
     * 批量修改树形编码
     * @param treeCode
     * @param idIndex
     * @param treeLevelDiff
     * @return
     */
    int updateTreeCodes(@Param("treeCode") String treeCode, @Param("idIndex") Integer idIndex, @Param("treeLevelDiff") Integer treeLevelDiff);

    /**
     * 通过userId和shopId获取分销关系
     * @param userId    用户id
     * @param shopId    小铺id
     * @return  SfUserRelation
     */
    SfUserRelation getSfUserRelationByUserIdAndShopId(@Param("userId") Long userId, @Param("shopId") Long shopId);

    List<Map<String, Number>> selectFansNumGroupByLevel(@Param("userId") Long userPid,
                                                        @Param("userLevel") Integer userLevel,
                                                        @Param("shopId") Long shopId,
                                                        @Param("sopkenMan") Integer sopkenMan);


    /**
     * 通过ID查询小铺中的代言人信息
     * @param shopId    小铺id
     * @param ID        代言人ID
     * @return
     */
    List<SfSpokesAndFansInfo> selectSpokesManByID(@Param("shopId") Long shopId,
                                                  @Param("ID") String ID,
                                                  @Param("sopkenMan") Integer sopkenMan);

    /**
     * 通过ID查询小铺中的代言人数量
     * @param shopId    小铺id
     * @param ID        代言人ID
     * @return
     */
    Integer selectSpokesManNumByID(@Param("shopId") Long shopId,
                                   @Param("ID") String ID,
                                   @Param("spokesMan") Integer spokesMan);

    SfSpokesAndFansInfo selectSfSpokesAndFansInfo(@Param("shopId") Long shopId,
                                                  @Param("userId") Long userId);

   /**
    * 查询某个店铺所有粉丝数量(排除店主自己)
    *
    * @param shopId    店铺id
    * @param userId
    * @return
    */
    Integer selectFansNumsByShopId(@Param("shopId") Long shopId,
                                   @Param("userId") Long userId);

    /**
     * 根据店铺id查找所有粉丝关系
     *
     * @param shopId
     * @return
     */
    List<SfUserRelation> selectAllFansRelationByShopId(@Param("shopId") Long shopId,
                                                       @Param("userId") Long userId);

    /**
     * 查询某个店铺所有代言人数量(排除店主自己)
     *
     * @param shopId
     * @param userId
     * @return
     */
    Integer selectSfSpokesManNumByShopId(@Param("shopId") Long shopId,
                                         @Param("userId") Long userId);

    /**
     * 根据店铺id查找所有代言人关系(排除店主)
     *
     * @param shopId
     * @param userId
     * @return
     */
    List<SfUserRelation> selectAllSpokeManRelationByShopId(@Param("shopId") Long shopId,
                                                           @Param("userId") Long userId);
}