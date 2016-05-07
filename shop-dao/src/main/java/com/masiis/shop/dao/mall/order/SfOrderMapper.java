/*
 * SfOrderMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-04-08 Created
 */
package com.masiis.shop.dao.mall.order;

import com.masiis.shop.dao.po.SfOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface SfOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SfOrder record);

    SfOrder selectByPrimaryKey(Long id);

    List<SfOrder> selectAll();

    int updateByPrimaryKey(SfOrder record);

    SfOrder selectByOrderCode(String orderCode);

    /**
     * 统计店铺订单量
     * @param shopId
     * @return
     */
    Integer countByShopId(Long shopId);

    /**
     * 根据条件查询订单
     * @param conditionMap
     * @return
     */
    List<SfOrder> selectByMap(Map<String, Object> conditionMap);

    /**
     * 查询指定时间,指定状态,指定支付状态的小铺订单
     *
     * @param expiraTime
     * @param orderStatus
     * @param payStatus
     * @return
     */
    List<SfOrder> selectByStatusAndDate(@Param("expiraTime") Date expiraTime,
                                        @Param("orderStatus") int orderStatus,
                                        @Param("payStatus") int payStatus);

    /**
     *更新小铺订单状态
     *
     * @param orderId
     * @return
     */
    int updateOrderCancelById(@Param("orderId") Long orderId,
                              @Param("orderStatus") Integer orderStatus);

    /**
     * 查询出C端没有提现权限用户的小铺订单
     * @param userId
     * @param status
     * @return
     */
    SfOrder selectNotIsBuyByUserId(Long userId,Integer status);
}