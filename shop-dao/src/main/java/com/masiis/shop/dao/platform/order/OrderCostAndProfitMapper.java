package com.masiis.shop.dao.platform.order;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by wangbingjian on 2016/6/7.
 */
@Repository
public interface OrderCostAndProfitMapper {

    /**
     * 代理已结算利润
     * @param userPid
     * @return
     */
    Map<String,String> pfOrderCostAndProfit(@Param("userPid") Long userPid);

    /**
     * 小铺已结算利润
     * @param userId
     * @return
     */
    Map<String,String> sfOrderCostAndProfit(@Param("userId") Long userId);
}
