package com.masiis.shop.admin.service.statistic;

import com.masiis.shop.dao.beans.statistic.StatisticOrder;
import com.masiis.shop.dao.beans.statistic.StatisticUser;
import com.masiis.shop.dao.platform.statistic.StatisticOrderMapper;
import com.masiis.shop.dao.platform.statistic.StatisticUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
 * Created by cai_tb on 16/8/29.
 */
@Service
public class StatisticUserService {

    @Resource
    private StatisticUserMapper staticUserMapper;

    @Resource
    private StatisticOrderMapper statisticOrderMapper;


    public List<StatisticUser> userList(String beginTime, String endTime, String appid) {
        return  staticUserMapper.userList(beginTime, endTime, appid);
    }

    public List<StatisticUser> newUserList(String beginTime, String endTime, String appid) {
        return staticUserMapper.newUserList(beginTime, endTime, appid);
    }

    public List<StatisticUser> useList(String beginTime, String endTime, String appid) {
        return staticUserMapper.useList(beginTime, endTime, appid);
    }

    public List<StatisticUser> newUseList(String beginTime, String endTime, String appid) {
        return staticUserMapper.newUseList(beginTime, endTime, appid);
    }

    public List<StatisticUser> cityNum(String beginTime, String endTime, String appid) {
        return staticUserMapper.cityNum(beginTime, endTime, appid);
    }

    public List<StatisticUser> newCityNum(String beginTime, String endTime, String appid) {
        return staticUserMapper.newCityNum(beginTime, endTime, appid);
    }

    public List<StatisticOrder> selectOrder(String beginTime, String endTime, String appid) {
        return statisticOrderMapper.selectOrder(beginTime, endTime, appid);
    }

    public List<StatisticOrder> newSelectOrder(String beginTime, String endTime, String appid) {
        return statisticOrderMapper.newSelectOrder(beginTime, endTime, appid);
    }

    public List<StatisticOrder> cityOrder(String beginTime, String endTime, String appid) {
        return statisticOrderMapper.cityOrder(beginTime, endTime, appid);
    }

    public List<StatisticOrder> newCityOrder(String beginTime, String endTime, String appid) {
        return statisticOrderMapper.newCityOrder(beginTime, endTime, appid);
    }
}
