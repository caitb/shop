/*
 * PfUserSkuMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package com.masiis.shop.dao.platform.user;

import com.masiis.shop.dao.beans.family.TeamMemberInfo;
import com.masiis.shop.dao.beans.user.PfUserSkuCertificate;
import com.masiis.shop.dao.beans.user.UserSkuInfo;
import com.masiis.shop.dao.po.PfSkuAgent;
import com.masiis.shop.dao.po.PfUserSku;
import com.masiis.shop.dao.beans.user.upgrade.UserSkuAgent;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
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

    List<Map<String, Object>> selectByMap(Map<String, Object> conditionMap);

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

    /**
     * 查询这个人的boss的团队的所有成员
     * @param treeCode
     * @return
     */
    List<PfUserSku> getBossTeamInfoByTreeCode(String treeCode);

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
     * 根据父用户id查询下级代理人数
     *
     * @param userPid
     * @return
     */
    Integer selectChildNumByUserPid(@Param("userPid") Long userPid);

    /**
     * 根据userPid查询直接下级
     *
     * @param userId
     * @return
     */
    List<Long> selectChildByUserPid(Long userId);

    /**
     * 根据用户id查询该用户代理的商品
     *
     * @param userId
     * @return
     */
    List<UserSkuInfo> selectSkusByUserId(@Param("userId") Long userId);

    PfUserSku selectMaxAgentIdByUserId(@Param("userId") Long userId);

    Integer selectAgentNumByLevelAndSku(@Param("agentLevel") Integer agentLevel,
                                        @Param("skuId") Integer skuId);

    List<PfUserSku> selectNumByBrandId(Integer brandId);

    List<Map<String,Object>> getLowerLevelPartnerListByUserPid(Long userPid);

    /**
     * 查找家族user
     * @return
     */
    PfUserSku selectFamilyUser(@Param("skuId")Integer skuId, @Param("agentLevelId")Integer agentLevelId, @Param("treeCode")String treeCode);

    /**
     * 获取代理的品牌
     * @param userId
     * @param brandId
     * @return
     */
    List<Map<String, Object>> selectAgentBrand(@Param("userId")Long userId, @Param("brandId")Integer brandId);

    /**
     * 获取上级代理的品牌
     * @param userId
     * @param brandId
     * @return
     */
    List<Map<String, Object>> selectPAgentBrand(@Param("userId")Long userId, @Param("brandId")Integer brandId);


    List<PfSkuAgent> selectOthersByUserIdAndDefaultSkuId(@Param("userId") Long userId,
                                                         @Param("skuId") Integer skuId);

    int countAgentNumBySkuId(Integer skuId);

    /**
     * 獲取用戶代理品牌下所代理的商品
     * @param userId
     * @param brandId
     * @return
     */
    List<PfUserSku> selectBrandSku(@Param("userId") Long userId,
                                   @Param("brandId") Integer brandId);

    List<Map<String, Number>> selectUpBrand(@Param("userId") Long userId);

    /**
     * 根据id查询总人数
     * @param id       id
     * @param userId  当userId为null时 查询的是商品团队总数（包含自己）
     *                当userId不为null时 查询的是不包含自己的商品团队总数
     * @return
     */
    Integer selectTeamCountById(@Param("id") Integer id, @Param("userId") Long userId);

    /**
     * 通过userId查询所有产品下级代理人数
     * @param userId
     * @return
     */
    Integer selectTeamCountByUserId(@Param("userId") Long userId);

    /**
     * 根据id查询直接下级代理人数
     * @param id
     * @return
     */
    Integer selectdirectNumById(@Param("id") Integer id);

    /**
     * 根据userid查询所有产品直接下级代理人数
     * @param userId
     * @return
     */
    Integer selectdirectNumByUserId(@Param("userId") Long userId);

    /**
     * 查询直接下级的团队列表
     * @param userSkuId
     * @return
     */
    List<Map<String,Object>> selectDirectListByuserId(@Param("userSkuId") Integer userSkuId);

    TeamMemberInfo selectMemberInfo(@Param("userSkuId") Integer userSkuId);


    List<PfUserSku> selectPrimarySkuByUserId(Long userId);

    Integer selectGTLastLevelNumByUserId(@Param("userId") Long userId);

    /**
     * 根据userId和品牌id查询下级id
     *
     * @param userId
     * @param brandId
     * @return
     */
    List<Long> selectChildsByUserIdAndBrandId(@Param("userId")Long userId, @Param("brandId") Integer brandId);

    /**
     * 查询代理最早的一款产品
     * @param userId    userId
     * @return
     */
    PfUserSku selectFirstPfUserSku(@Param("userId") Long userId);

    /**
     * 根据用户id 和品牌id 查询这个用户这个品牌下的非主打商品
     * @param userId
     * @param brandId
     * @return
     */
    List<PfUserSku> getNoMainUserSkuByUserIdAndBrandId(@Param("userId")Long userId, @Param("brandId") Integer brandId);

    /**
     * 查找时间之前的没有生成证书的记录
     *
     * @return
     */
    List<PfUserSku> selectWithNoCode(@Param("time") Date time);
}