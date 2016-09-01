/*
 * PfSkuAgentMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-04 Created
 */
package com.masiis.shop.dao.platform.product;

import com.masiis.shop.dao.beans.user.upgrade.UpgradePackageInfo;
import com.masiis.shop.dao.po.PfSkuAgent;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
public interface PfSkuAgentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PfSkuAgent record);
    BigDecimal selectMaxBail(Integer skuId);
    BigDecimal selectMinBail(Integer skuId);

    PfSkuAgent selectByPrimaryKey(Integer id);

    List<PfSkuAgent> selectAll();

    int updateByPrimaryKey(PfSkuAgent record);

    List<PfSkuAgent> selectAllBySkuId(Integer sku_id);

    PfSkuAgent selectBySkuIdAndLevelId(@Param("skuId") Integer skuId, @Param("levelId") Integer levelId);

    List<PfSkuAgent> selectBySkuId(Integer skuId);

    void updateById(PfSkuAgent pfSkuAgent);

    /*获取用户商品等级标志*/
    List<PfSkuAgent> getSkuLevelIconByUserId(Long userId);

    List<Map<String,String>> selectBrandLevelIconByUserId(@Param("userId") Long userId);

    /**
     * 统计商品代理等级数
     * @param skuId
     * @return
     */
    Integer countSkuAgentLevel(Integer skuId);

    List<PfSkuAgent> selectUpgradeAgents(@Param("skuId") Integer skuId,
                                         @Param("agentLevelId") Integer agentLevelId,
                                         @Param("pLevelId") Integer pLevelId);

    List<UpgradePackageInfo> selectUpgradePackage(@Param("skuId") Integer skuId,
                                                  @Param("agentLevelId") Integer agentLevelId,
                                                  @Param("pLevelId") Integer pLevelId);
    /**
     * 通过skuId查找代理等级id
     * @param skuId
     * @param isShow
     * @return
     */
    List<Integer> selectLevelIdsBySkuIdAndIsShow(@Param("skuId")Integer skuId, @Param("isShow")Integer isShow);

    /**
     * 根据skuid查找小于指定等级id的代理信息
     *
     * @param agentLevelId
     * @param skuId
     * @return
     */
    List<PfSkuAgent> selectLessLevelByLevelIdAndSkuId(@Param("agentLevelId") Integer agentLevelId,
                                                      @Param("skuId") Integer skuId);

    /**
     *  条件查询
     * @param conditionMap
     * @return
     */
    List<Map<String, Object>> selectByCondition(Map<String, Object> conditionMap);


}