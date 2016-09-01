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

    private String beginTime;//加入时间

    private String backimg;//证书等级背景图

    private String levelName;//合伙人等级名称

    private String skuIcon;//商品lOGO

    private String brandIcon;//品牌的logo

    private Integer brandId;//品牌id

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

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getBackimg() {
        return backimg;
    }

    public void setBackimg(String backimg) {
        this.backimg = backimg;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getSkuIcon() {
        return skuIcon;
    }

    public void setSkuIcon(String skuIcon) {
        this.skuIcon = skuIcon;
    }

    public String getBrandIcon() {
        return brandIcon;
    }

    public void setBrandIcon(String brandIcon) {
        this.brandIcon = brandIcon;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }
}
