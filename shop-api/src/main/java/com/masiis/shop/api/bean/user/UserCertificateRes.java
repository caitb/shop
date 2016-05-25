package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BaseRes;
import com.masiis.shop.dao.beans.certificate.CertificateInfo;

import java.util.List;


/**
 * Created by JingHao on 2016/5/25 0025.
 */
public class UserCertificateRes extends BaseRes{

    private List<CertificateInfo> certificateInfoList;

    public List<CertificateInfo> getCertificateInfoList() {
        return certificateInfoList;
    }

    public void setCertificateInfoList(List<CertificateInfo> certificateInfoList) {
        this.certificateInfoList = certificateInfoList;
    }
}
