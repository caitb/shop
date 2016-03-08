package com.masiis.shop.admin.service.certificate;

import com.masiis.shop.dao.beans.certificate.CertificateInfo;
import com.masiis.shop.dao.platform.certificate.CertificateMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by JingHao on 2016/3/7 0007.
 */
@Service
@Transactional
public class CertificateService {


    @Resource
    private CertificateMapper certificateMapper;

    /**
      * @Author 贾晶豪
      * @Date 2016/3/7 0007 下午 5:51
      * 授权书列表
      */
    public List<CertificateInfo> getCertificates() {
        return certificateMapper.getCertificateInfo();
    }

}
