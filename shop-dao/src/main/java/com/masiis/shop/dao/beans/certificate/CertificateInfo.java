package com.masiis.shop.dao.beans.certificate;

import com.masiis.shop.dao.po.PfUserCertificate;

/**
 * Created by JingHao on 2016/3/7 0007.
 */
public class CertificateInfo extends PfUserCertificate {

    private String ctName;// 姓名

    public String getCtName() {
        return ctName;
    }

    public void setCtName(String ctName) {
        this.ctName = ctName;
    }
}
