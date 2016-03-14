package com.masiis.shop.dao.platform.certificate;

import com.masiis.shop.dao.beans.certificate.CertificateInfo;
import com.masiis.shop.dao.po.ComUser;

import java.util.List;
import java.util.Map;

/**
 * Created by JingHao on 2016/3/7 0007.
 */
public interface CertificateMapper {

    List<CertificateInfo> getCertificateInfo(Map<String, Object> paramsMap);

    void updateCertificateFlag(Map<String, Object> paramsMap);

    void updateCertificateStatus(Map<String, Object> paramsMap);

    CertificateInfo get(Integer id);

    CertificateInfo getApproveInfo(Integer id);

    void updateCertificateImgUrl(Map<String, Object> paramsMap);

    void updateUpperPartnerById(Map<String, Object> paramsMap);

    List<ComUser> getUpperPartnerByUserId(Integer pid);

}
