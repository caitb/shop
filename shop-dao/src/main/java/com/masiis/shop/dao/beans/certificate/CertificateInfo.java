package com.masiis.shop.dao.beans.certificate;

import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfBorder;
import com.masiis.shop.dao.po.PfUserCertificate;
import com.masiis.shop.dao.po.PfUserSku;

import java.util.List;

/**
 * Created by JingHao on 2016/3/7 0007.
 */
public class CertificateInfo extends PfUserSku {

    private String ctName;// 姓名

    private String skuName;//合伙商品名称

    private PfUserCertificate pfUserCertificateInfo; //授权书相关属性

    private Integer parentUserId; //父ID

    private String upperName;//上级合伙人

    private String approveType;//审核类型: 1 平台审核 ,2 合伙人审核

    private PfBorder pfBorder;//代理订单属性

    private ComUser comUser;//用户信息

    private List<ComUser> comUserList;//合伙人列表

    public String getCtName() {
        return ctName;
    }

    public void setCtName(String ctName) {
        this.ctName = ctName;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public PfUserCertificate getPfUserCertificateInfo() {
        return pfUserCertificateInfo;
    }

    public void setPfUserCertificateInfo(PfUserCertificate pfUserCertificateInfo) {
        this.pfUserCertificateInfo = pfUserCertificateInfo;
    }

    public Integer getParentUserId() {
        return parentUserId;
    }

    public void setParentUserId(Integer parentUserId) {
        this.parentUserId = parentUserId;
    }

    public String getApproveType() {
        return approveType;
    }

    public void setApproveType(String approveType) {
        this.approveType = approveType;
    }

    public PfBorder getPfBorder() {
        return pfBorder;
    }

    public void setPfBorder(PfBorder pfBorder) {
        this.pfBorder = pfBorder;
    }

    public ComUser getComUser() {
        return comUser;
    }

    public void setComUser(ComUser comUser) {
        this.comUser = comUser;
    }

    public String getUpperName() {
        return upperName;
    }

    public void setUpperName(String upperName) {
        this.upperName = upperName;
    }

    public List<ComUser> getComUserList() {
        return comUserList;
    }

    public void setComUserList(List<ComUser> comUserList) {
        this.comUserList = comUserList;
    }
}
