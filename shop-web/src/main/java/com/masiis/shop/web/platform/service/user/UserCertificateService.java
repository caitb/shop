package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.dao.platform.user.PfUserCertificateMapper;
import com.masiis.shop.dao.po.PfUserCertificate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * UserCertificateService
 *
 * @author ZhaoLiang
 * @date 2016/3/11
 */
@Service
@Transactional
public class UserCertificateService {

    @Resource
    private PfUserCertificateMapper pfUserCertificateMapper;

    public void addUserCertificate(PfUserCertificate pfUserCertificate) {
        pfUserCertificateMapper.insert(pfUserCertificate);
    }
}
