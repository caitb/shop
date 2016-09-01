package com.masiis.shop.admin.service.user;

import com.masiis.shop.dao.platform.certificate.ComBrandCertificateMapper;
import com.masiis.shop.dao.po.ComBrandCertificate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by hzz on 2016/8/18.
 */
@Service
public class ComBrandCertificateService {

    @Resource
    private ComBrandCertificateMapper brandCertificateMapper;

    public ComBrandCertificate getBrandCertificateByBrandIdAndLevelId(Integer brandId, Integer agentLevelId){
        return brandCertificateMapper.getBrandCertificateByBrandIdAndLevelId(brandId,agentLevelId);
    }
}
