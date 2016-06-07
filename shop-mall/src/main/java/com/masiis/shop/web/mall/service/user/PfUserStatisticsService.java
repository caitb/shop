package com.masiis.shop.web.mall.service.user;

import com.masiis.shop.dao.platform.user.PfUserStatisticsMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfUserStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by hzz on 2016/6/6.
 */
@Service
@Transactional
public class PfUserStatisticsService {

    @Autowired
    private PfUserStatisticsMapper userStatisticsMapper;

    public PfUserStatistics selectByPrimaryKey(Long id){
        return userStatisticsMapper.selectByPrimaryKey(id);
    }

    public PfUserStatistics initPfUserStatistics(ComUser user){
        PfUserStatistics statistics = new PfUserStatistics();

        statistics.setUserId(user.getId());
        statistics.setCreateTime(new Date());
        statistics.setCostFee(BigDecimal.ZERO);
        statistics.setDownOrderCount(0);
        statistics.setDownProductCount(0);
        statistics.setIncomeFee(BigDecimal.ZERO);
        statistics.setProfitFee(BigDecimal.ZERO);
        statistics.setTakeFee(BigDecimal.ZERO);
        statistics.setTakeOrderCount(0);
        statistics.setTakeProductCount(0);
        statistics.setUpOrderCount(0);
        statistics.setUpProductCount(0);
        statistics.setVersion(0l);
        userStatisticsMapper.insert(statistics);

        return statistics;
    }
}
