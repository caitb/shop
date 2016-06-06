package com.masiis.shop.web.mall.service.shop;

import com.masiis.shop.dao.mall.shop.SfShopStatisticsMapper;
import com.masiis.shop.dao.po.SfShopStatistics;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by hzz on 2016/6/6.
 */
@Service
@Transactional
public class SfShopStatisticsService {

    @Resource
    private SfShopStatisticsMapper statisticsMapper;

    public SfShopStatistics selectByShopUserId(Long shopUserId){
        return statisticsMapper.selectByShopUserId(shopUserId);
    }
}
