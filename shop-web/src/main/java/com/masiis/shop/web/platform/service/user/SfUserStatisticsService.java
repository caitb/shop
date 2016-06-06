package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.dao.mall.user.SfUserStatisticsMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.SfUserStatistics;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Date 2016/6/6
 * @Auther lzh
 */
@Service
public class SfUserStatisticsService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private SfUserStatisticsMapper sfUserStatisticsMapper;

    public SfUserStatistics initSfUserStatistics(ComUser user){
        SfUserStatistics statistics = new SfUserStatistics();

        statistics.setBuyFee(BigDecimal.ZERO);
        statistics.setCreateTime(new Date());
        statistics.setDistributionFee(BigDecimal.ZERO);
        statistics.setOrderCount(0);
        statistics.setWithdrawFee(BigDecimal.ZERO);
        statistics.setUserId(user.getId());
        sfUserStatisticsMapper.insert(statistics);

        return statistics;
    }
}
