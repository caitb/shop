/*
 * PfBorderRecommenRewardMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-06-14 Created
 */
package com.masiis.shop.dao.platform.order;

import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfBorderRecommenReward;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PfBorderRecommenRewardMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PfBorderRecommenReward record);

    PfBorderRecommenReward selectByPrimaryKey(Long id);

    PfBorderRecommenReward selectByBorderItemId(Long pfBorderItemId);
    PfBorderRecommenReward getRewardByOrderIdAndOrderItemIdAndSkuId(@Param("orderId")Long orderId,@Param("orderItemId")Long orderItemId,@Param("skuId")Integer skuId);

    List<PfBorderRecommenReward> selectAll();

    int updateByPrimaryKey(PfBorderRecommenReward record);
    /**
     * 获得奖励订单
     * @author muchaofeng
     */
    int selectBorders(Long userId);
    /**
     * 发出奖励订单
     * @author muchaofeng
     * @date 2016/6/15 15:36
     */
    int selectPBorders(Long userId);

    /**
     * 根据代理订单查询推荐奖励明细
     *
     * @param pfBorderId
     * @return
     */
    List<PfBorderRecommenReward> selectByPfBorderId(@Param("pfBorderId") Long pfBorderId);

    /**
     * 根据订单id查找推荐人
     * @param borderId
     * @return
     */
    ComUser selectRecommenUser(Long borderId);
}