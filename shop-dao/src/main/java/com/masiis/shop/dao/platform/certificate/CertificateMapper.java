package com.masiis.shop.dao.platform.certificate;

import com.masiis.shop.dao.beans.certificate.CertificateInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by JingHao on 2016/3/7 0007.
 */
@Repository
public interface CertificateMapper {

    List<CertificateInfo> getCertificateInfo(Map<String, Object> paramsMap);

    void updateCertificateFlag(Map<String, Object> paramsMap);

    void updateCertificateStatus(Map<String, Object> paramsMap);

    CertificateInfo get(Integer id);

    void updateCertificateImgUrl(Map<String, Object> paramsMap);

    void updateUpperPartnerById(Map<String, Object> paramsMap);

    String findById(Integer id);

    List<CertificateInfo> getCertificatesByUser(Long userId);
}
