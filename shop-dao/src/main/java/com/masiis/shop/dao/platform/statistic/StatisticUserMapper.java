package com.masiis.shop.dao.platform.statistic;

import com.masiis.shop.dao.beans.statistic.StatisticUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * Created by cai_tb on 16/8/29.
 */
public interface StatisticUserMapper {

    //注册用户量
    List<StatisticUser> userList(@Param("beginTime")String beginTime, @Param("endTime")String endTime, @Param("appid") String appid);

    //新增用户量
    List newUserList(@Param("beginTime")String beginTime, @Param("endTime")String endTime, @Param("appid") String appid);

    //平台使用用户量（人）（麦链合伙人：合伙人数）
    List useList(@Param("beginTime")String beginTime, @Param("endTime")String endTime, @Param("appid") String appid);

    //平台使用用户量（人）（麦链合伙人：新增合伙人数）
    List newUseList(@Param("beginTime")String beginTime, @Param("endTime")String endTime, @Param("appid") String appid);

    //麦链商城购买人数
    List<StatisticUser> cityNum(@Param("beginTime")String beginTime, @Param("endTime")String endTime, @Param("appid") String appid);

    //麦链商城新增购买人数
    List newCityNum(@Param("beginTime")String beginTime, @Param("endTime")String endTime, @Param("appid") String appid);
}
