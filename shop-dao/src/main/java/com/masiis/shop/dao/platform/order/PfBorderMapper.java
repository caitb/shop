/*
 * PfBorderMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package com.masiis.shop.dao.platform.order;

import com.masiis.shop.dao.po.PfBorder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface PfBorderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PfBorder record);

    PfBorder selectByPrimaryKey(Long id);

    List<PfBorder> selectAll();

    int updateById(PfBorder pfBorder);

    PfBorder selectByOrderCode(String orderId);

    List<PfBorder> selectByUserId(@Param("userId") Long userId, @Param("orderStatus") Integer orderStatus, @Param("sendType") Integer sendType);

    List<PfBorder> selectByCondition(PfBorder pfBorder);

    /**
     * 获得奖励订单
     * @author muchaofeng
     * @date 2016/6/16 14:25
     */
    List<PfBorder> selectRecommend(@Param("userId")Long userId, @Param("skuId")Integer skuId);

    /**
     * 发出奖励订单
     * @author muchaofeng
     * @date 2016/6/16 16:43
     */
    List<PfBorder> selectSendRecommend(@Param("userId")Long userId, @Param("skuId")Integer skuId);

    List<PfBorder> selectByUserPid(@Param("userPId") Long userPId, @Param("orderStatus") Integer orderStatus, @Param("sendType") Integer sendType);


    List<PfBorder> selectUnCountingByUserAndDate(@Param("userid") Long userid,
                                                 @Param("start") Date countStartDay,
                                                 @Param("end") Date countEndDay);

    /**
     * 根据订单创建时间的上限,订单状态和支付状态来查询订单
     *
     * @param expiraTime
     * @param orderStatus
     * @param payStatus
     * @return
     */
    List<PfBorder> selectByStatusAndDate(@Param("expiraTime") Date expiraTime,
                                         @Param("orderStatus") Integer orderStatus,
                                         @Param("payStatus") Integer payStatus);

    /**
     * 取消代理订单
     *
     * @param orderId
     * @return
     */
    int updateOrderCancelById(@Param("orderId") Long orderId);

    /**
     * 统计团队销售额
     *
     * @param sUserIds
     * @return
     */
    Double countSales(String sUserIds);

    /**
     * 统计代理商购买次数和购买总额
     *
     * @param userId
     * @return
     */
    Map<String, Double> statisticsBuy(@Param("userId")Long userId, @Param("userPid")Long userPid, @Param("skuId")Integer skuId);

    Integer selectQueuingOrderCount(@Param("skuId") Integer skuId);

    /**
     * 查询排单订单
     * @return
     */
    List<PfBorder> selectAllQueuingOrder(@Param("status") Integer status);

    /**
     * 根据上级userid和订单状态查询订单数量
     *
     * @param userPid
     * @param orderStatus
     * @return
     */
    Integer queryOrderNumsByUpidAndStatus(@Param("userPid") Long userPid,
                                          @Param("orderStatus") Integer orderStatus);

    /**
     * 根据商品Id和用户Id 查询订单信息
     */
    PfBorder selectPfBOrderBySkuIdAndUserId(@Param("skuId") Integer skuId,@Param("userId") Long userId);

    /**
     * 根据orderid来取消线下支付订单
     *
     * @param orderId
     * @return
     */
    int updateOfflineBOrderCancelById(@Param("orderId") Long orderId);

    /**
     * 根据订单创建时间的上限,订单状态和支付状态来查询非升级订单
     *
     * @param expiraTime
     * @param orderStatus
     * @param payStatus
     * @return
     */
    List<PfBorder> selectUnUpgradeByStatusAndDate(@Param("expiraTime") Date expiraTime,
                                                  @Param("orderStatus") Integer orderStatus,
                                                  @Param("payStatus") Integer payStatus,
                                                  @Param("notOrderType") Integer notOrderType);

    /**
     * 根据订单创建时间的上限,订单状态和支付状态和订单状态来查询升级订单
     *
     * @param expiraTime
     * @param orderStatus
     * @param payStatus
     * @param orderType
     * @return
     */
    List<PfBorder> selectByStatusAndDateAndType(@Param("expiraTime") Date expiraTime,
                                                @Param("orderStatus") Integer orderStatus,
                                                @Param("payStatus") Integer payStatus,
                                                @Param("orderType") Integer orderType);

    int updateCancelByIdAndOStatusAndPStatusAndOType(@Param("orderId") Long orderId,
                                                      @Param("orderStatus") Integer orderStatus,
                                                      @Param("payStatus") Integer payStatus,
                                                      @Param("orderType") Integer orderType);
}