package com.masiis.shop.dao.platform.certificate;

import com.masiis.shop.dao.beans.certificate.CertificateInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by JingHao on 2016/3/7 0007.
 */
public interface CertificateMapper {

    List<CertificateInfo> getCertificateInfo(Map<String, Object> paramsMap);

    void updateCertificateFlag(Integer id);

    void updateCertificateStatus(Map<String, Object> paramsMap);

    CertificateInfo get(Integer id);

    CertificateInfo getApproveInfoById(Integer id);
}
