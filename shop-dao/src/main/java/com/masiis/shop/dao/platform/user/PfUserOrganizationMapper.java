/*
 * PfUserOrganizationMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-08-04 Created
 */
package com.masiis.shop.dao.platform.user;

import com.masiis.shop.dao.beans.message.PfMessageToNewBean;
import com.masiis.shop.dao.po.PfUserOrganization;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface PfUserOrganizationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PfUserOrganization record);

    PfUserOrganization selectByPrimaryKey(Integer id);

    List<PfUserOrganization> selectAll();

    int updateByPrimaryKey(PfUserOrganization record);

    List<PfUserOrganization> selectOrganizationByUserId(Long userId);

    List<PfUserOrganization> selectAllFamily(@Param("agentLevelId")Integer agentLevelId, @Param("type")Integer type);

    List<PfUserOrganization> selectAllFamilyByAgentLevelId(@Param("agentLevelId")Integer agentLevelId);

    Map<String, Object> selectByMobile(@Param("mobile")String mobile,@Param("brandId")Integer brandId);

    PfUserOrganization selectByBrandIdAndUserId(@Param("brandId")Integer brandId, @Param("userId")Long userId);

    List<PfUserOrganization> selectAllFamilyByAgentLevelIdAndBrandId(@Param("agentLevelId")Integer agentLevelId,@Param("brandId")Integer brandId);

    List<PfUserOrganization> selectOrganization(@Param("userId") Long userId);

    List<PfUserOrganization> selectOrganizationMap(@Param("brandMaps") Map<Integer, Map<Long, Long>> brandMaps);

    /**
     * 查找我加入的家族
     * @param userIds
     * @return
     */
    List<Map<String, Object>> selectFromOrganization(List<Long> userIds);

    /**
     * 查询我的家族或团队
     * @param userId
     * @return
     */
    List<Map<String, Object>> selectByUserId(Long userId);

    /**
     * 查询我创建的团队
     * @param userId
     * @return
     */
    List<Map<String, Object>> selectCreateOrganization(Long userId);

    /**
     * 查询我加入的团队和家族
     * @param userId
     * @return
     */
    List<Map<String, Object>> selectJoinOrganization(@Param("userId")Long userId);

    /**
     * 查询我创建的和直接上级的家族
     * @param userId
     * @return
     */
    List<Map<String, Object>> selectCreateAndJoinOrganization(Long userId);

    /**
     * 统计团队人数和总销售额
     * @param userId
     * @return
     */
    Map<String, Object> countByUserId(@Param("userId")Long userId, @Param("brandId")Integer brandId);

    List<PfUserOrganization> select(@Param("organization") PfUserOrganization organization);

    List<PfUserOrganization> selectParentByUserIdAndLevelId(@Param("userId") Long userId, @Param("levelId") Integer levelId);

    List<Map<String, Object>> listCreateAndJoinOrganization(Long userId);

    /**
     * 查询某人的各个家族直接下级人数
     *
     * @param userId
     * @return
     */
    List<PfMessageToNewBean> selectNumsGroupByOrganization(@Param("userId") Long userId);
}