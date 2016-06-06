package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.dao.platform.user.PfUserCertificateMapper;
import com.masiis.shop.dao.platform.user.PfUserStatisticsMapper;
import com.masiis.shop.dao.po.PfUserCertificate;
import com.masiis.shop.dao.po.PfUserStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
