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

    private String realName;//所属人姓名

    private String skuName;//合伙商品

    private String ctname;//合伙人等级

    private String sjName;//上级合伙人姓名

    private String imgUrl;//证书路径

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getCtname() {
        return ctname;
    }

    public void setCtname(String ctname) {
        this.ctname = ctname;
    }

    public String getSjName() {
        return sjName;
    }

    public void setSjName(String sjName) {
        this.sjName = sjName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
