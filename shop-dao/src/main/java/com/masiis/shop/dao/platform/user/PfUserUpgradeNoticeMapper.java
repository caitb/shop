/*
 * PfUserUpgradeNoticeMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-06-14 Created
 */
package com.masiis.shop.dao.platform.user;

import com.masiis.shop.dao.po.PfUserUpgradeNotice;
import org.springframework.stereotype.Repository;
import com.masiis.shop.dao.beans.user.upgrade.UpGradeInfoPo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface PfUserUpgradeNoticeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PfUserUpgradeNotice record);

    PfUserUpgradeNotice selectByPrimaryKey(Long id);

    PfUserUpgradeNotice selectByPfBorderId(Long orderId);

    List<PfUserUpgradeNotice> selectAll();

    int updateByPrimaryKey(PfUserUpgradeNotice record);

    List<PfUserUpgradeNotice> selectByUserId(Long userId);

    List<PfUserUpgradeNotice> selectByUserPId(Long userPid);

    List<PfUserUpgradeNotice> selectByUserPidAndStatus(@Param("userPid")Long userPid, @Param("status")Integer status);

    List<PfUserUpgradeNotice> selectByMap(Map<String,Object> conditionMap);

    List<Map<String, Object>> selectUpgradeRecordByUserIdAndSkuId(@Param("userId")Long userId, @Param("skuId")Integer skuId);

    List<PfUserUpgradeNotice> selectByParam(@Param("userPid") Long userPid, @Param("skuId") Integer skuId,@Param("upStatus") Integer upStatus);

    List<PfUserUpgradeNotice> selectBySkuIdAndRebateType(@Param("skuId") Integer skuId,@Param("userPid") Long userPid,@Param("userId") Long userId);

    List<PfUserUpgradeNotice> selectBySkuIdAndUserIdAndUserPid(@Param("skuId") Integer skuId,@Param("userPid") Long userPid,@Param("userId") Long userId);

    List<PfUserUpgradeNotice> selectByCondition(PfUserUpgradeNotice pfUserUpgradeNotice);

    PfUserUpgradeNotice selectLastUpgrade(@Param("userPid") Long userPid, @Param("skuId") Integer skuId, @Param("wishLevelId") Integer wishLevelId);

    /**
     * 查询指定上级处理状态和创建时间小于指定时间的通知单
     *
     * @param upStatus
     * @param time
     * @return
     */
    List<PfUserUpgradeNotice> selectUncalcelByUpStatusAndDate(@Param("upStatus") Integer upStatus,
                                                      @Param("time") Date time);

    UpGradeInfoPo selectUpGradeInfoPoById(@Param("Id") Long Id);

    List<PfUserUpgradeNotice> selectAllUnpayNoticesByDate(@Param("time") Date time);

    List<PfUserUpgradeNotice> selectAllUnpayOfflineNoticesByDate(@Param("time") Date time);

    /**
     * 根据父id和单据状态查询
     *
     * @param status
     * @param userPid
     * @return
     */
    List<PfUserUpgradeNotice> selectAllSubByStatusAndPid(@Param("status") Integer status,
                                                         @Param("userPid") Long userPid);

    List<PfUserUpgradeNotice> selectBySkuIdAndRebateALLType(@Param("skuId") Integer skuId,@Param("userId") Long userId);
}