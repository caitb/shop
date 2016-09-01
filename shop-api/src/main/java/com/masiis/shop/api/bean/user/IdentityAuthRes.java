package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BaseRes;

import java.io.InputStream;

/**
 * Created by hzz on 2016/5/24.
 */
public class IdentityAuthRes extends BaseRes {
    private String name; //姓名
    private String idCard; //身份证号码
    private String idCardFrontName; //正面名字
    private String idCardBackName; //反面名字
    private String idCardFrontUrl;//正面地址
    private String idCardBackUrl;//反面地址
    private String wxId; //微信号
    private String auditReason;//审核不通过原因
    private Boolean isAudit;//是否审核通过

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getIdCardFrontName() {
        return idCardFrontName;
    }

    public void setIdCardFrontName(String idCardFrontName) {
        this.idCardFrontName = idCardFrontName;
    }

    public String getIdCardBackName() {
        return idCardBackName;
    }

    public void setIdCardBackName(String idCardBackName) {
        this.idCardBackName = idCardBackName;
    }

    public String getIdCardFrontUrl() {
        return idCardFrontUrl;
    }

    public void setIdCardFrontUrl(String idCardFrontUrl) {
        this.idCardFrontUrl = idCardFrontUrl;
    }

    public String getIdCardBackUrl() {
        return idCardBackUrl;
    }

    public void setIdCardBackUrl(String idCardBackUrl) {
        this.idCardBackUrl = idCardBackUrl;
    }

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
    }

    public String getAuditReason() {
        return auditReason;
    }

    public void setAuditReason(String auditReason) {
        this.auditReason = auditReason;
    }

    public Boolean getAudit() {
        return isAudit;
    }

    public void setAudit(Boolean audit) {
        isAudit = audit;
    }
}
