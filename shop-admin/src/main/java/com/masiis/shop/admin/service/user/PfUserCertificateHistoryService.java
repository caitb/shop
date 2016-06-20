package com.masiis.shop.admin.service.user;

import com.masiis.shop.dao.platform.user.PfUserCertificateHistoryMapper;
import com.masiis.shop.dao.platform.user.PfUserCertificateMapper;
import com.masiis.shop.dao.po.PfUserCertificate;
import com.masiis.shop.dao.po.PfUserCertificateHistory;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by hzz on 2016/6/17.
 */
@Service
@Transactional
public class PfUserCertificateHistoryService {

    @Resource
    private PfUserCertificateHistoryMapper pfUserCertificateHistoryMapper;

    public int insert(PfUserCertificateHistory po){
        return pfUserCertificateHistoryMapper.insert(po);
    }
}
