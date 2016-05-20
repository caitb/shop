package com.masiis.shop.api.service.user;

import com.masiis.shop.dao.platform.user.PfUserCertificateMapper;
import com.masiis.shop.dao.po.PfUserCertificate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by cai_tb on 16/5/20.
 */
@Service
public class CertificateService {

    @Resource
    private PfUserCertificateMapper pfUserCertificateMapper;

    public PfUserCertificate getByCode(String code){
        return pfUserCertificateMapper.selectByCode(code);
    }
}
