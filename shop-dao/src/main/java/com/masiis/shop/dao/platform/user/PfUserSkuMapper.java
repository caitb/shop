/*
 * PfUserSkuMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package com.masiis.shop.dao.platform.user;

import com.masiis.shop.dao.beans.user.PfUserSkuCertificate;
import com.masiis.shop.dao.po.PfUserSku;
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
     * 获取用户代理商品种类的数量
     *
     * @return
     */
    Integer selectUserSkuCount(@Param("userId") Long userId, @Param("skuId") Integer skuId);

}