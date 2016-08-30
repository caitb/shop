package com.masiis.shop.dao.platform.statistic;

import com.masiis.shop.dao.beans.statistic.StatisticOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2016/8/29.
 */
public interface StatisticOrderMapper {

    //麦链合伙人成交订单量,成交金额
    List<StatisticOrder> selectOrder(@Param("beginTime")String beginTime, @Param("appid") String appid);

    //麦链合伙人新增成交订单量,新增成交金额
    List<StatisticOrder> newSelectOrder(@Param("beginTime")String beginTime, @Param("appid") String appid);

    //麦链商城成交订单量,成交金额
    List<StatisticOrder> cityOrder(@Param("beginTime")String beginTime, @Param("appid") String appid);

    //麦链商城新增成交订单量,新增成交金额
    List<StatisticOrder> newCityOrder(@Param("beginTime")String beginTime, @Param("appid") String appid);
}
