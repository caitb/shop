/*
 * PfUserSkuMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package com.masiis.shop.dao.platform.user;

import com.masiis.shop.dao.beans.user.PfUserSkuCertificate;
import com.masiis.shop.dao.beans.user.UserSkuInfo;
import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.PfUserSku;
import com.masiis.shop.dao.beans.user.upgrade.UserSkuAgent;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PfUserSkuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PfUserSku record);

    PfUserSku selectByPrimaryKey(Integer id);

    List<PfUserSku> selectAll();

    int updateByPrimaryKey(PfUserSku record);

    int updateResetAgentNum();

    List<PfUserSku> selectAgentNum(Long userId);

    PfUserSku selectByUserIdAndSkuId(@Param("userId") Long userId, @Param("skuId") Integer skuId);

    List<PfUserSkuCertificate> getUserSkuList(@Param("searchParam") Map<String, Object> searchParam);

    Integer findLowerCount(Integer pid);

    List<PfUserSkuCertificate> getUserSkuListById(@Param("id") Integer id);

    PfUserSku selectByOrderIdAndUserIdAndSkuId(@Param("bOrderId") Long bOrderId, @Param("userId") Long userId, @Param("skuId") Integer skuId);

    /**
     * 根据条件查询记录
     *
     * @param pfUserSku
     * @return
     */
    List<PfUserSku> selectByCondition(PfUserSku pfUserSku);

    /**
     * 统计直接下级人数
     *
     * @param pId
     * @return
     */
    List<Long> selectChildrenByPId(Integer pId);

    /**
     * 统计团队总人数
     *
     * @param sPIds
     * @return
     */
    Map<String, String> countChild(String sPIds);

    List<PfUserSku> selectByMap(Map<String, Object> conditionMap);

    /**
     * 查询倒数第二级列表
     *
     * @return
     */
    List<PfUserSku> selectSecondLastLevel();

    /**
     * 通过主键列表 查询
     *
     * @param list
     * @return
     */
    List<PfUserSku> selectByListId(List<Integer> list);

    /**
     * 根据代理登记查询
     *
     * @param level
     * @return
     */
    List<PfUserSku> selectByLevel(Integer level);

    /**
     * 获取用户代理商品种类的数量
     *
     * @return
     */
    Integer selectUserSkuCount(@Param("userId") Long userId, @Param("skuId") Integer skuId);


    /**
     * 修改树形编码
     * @param id 主键id
     * @param treeCode 属性编码
     * @return
     */
    int updateTreeCodeById(@Param("id") Integer id, @Param("treeCode") String treeCode);

    /**
     * 查找团队所有成员id
     * @param treeCode
     * @return
     */
    List<Long> selectAllTeamMember(String treeCode);

    List<UserSkuAgent> selectCurrentAgentLevel(@Param("userId") Long userId);


    List<PfUserSku> selectByUserId(@Param("userId") Long userId);

    /**
     * 批量修改树形编码
     * @param treeCode
     * @param idIndex
     * @param treeLevelDiff
     * @return
     */
    int updateTreeCodes(@Param("treeCode") String treeCode,@Param("parentTreeCode") String parentTreeCode, @Param("idIndex") Integer idIndex, @Param("treeLevelDiff") Integer treeLevelDiff);

    /**
     * 查询代理产品信息
     * @param userId
     * @return
     */
    List<Map<String, Object>> selectAgentSku(Long userId);

    List<PfUserSku> selectByUserIdNotPid(@Param("userId") Long userId);

    /**
     * 根据用户id查询该用户代理的商品
     *
     * @param userId
     * @return
     */
    List<UserSkuInfo> selectSkusByUserId(@Param("userId") Long userId);
}