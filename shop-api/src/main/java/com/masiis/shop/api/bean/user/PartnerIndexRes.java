package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BaseRes;
import com.masiis.shop.dao.po.PfUserOrganization;
import org.mybatis.generator.codegen.ibatis2.dao.templates.IbatisDAOTemplate;

import java.util.List;

/**
 * @Date 2016/5/3
 * @Auther lzh
 */
public class PartnerIndexRes extends BaseRes {
    private Integer isPartner;
    private Integer isBind;
    private Integer bOrderNums;
    private Integer partnerNums;//下级合伙人数量
    private String totalIncomeFee;//总销售额
    private String profitFee;//总利润
    private String nkName;
    private String userHeadImg;
    private String imgURl;
    private Integer countMsg;//消息数量
    private Integer upgradeNum;//升级数量

    private Integer auditStatus;//是否实名认证 0 未审核 1 审核中 2 审核通过 3 审核失败

    private Integer brandNum;//品牌数量

    private List<PfUserOrganization> pfUserOrganizations;
    public Integer getIsPartner() {
        return isPartner;
    }

    public void setIsPartner(Integer isPartner) {
        this.isPartner = isPartner;
    }

    public Integer getIsBind() {
        return isBind;
    }

    public void setIsBind(Integer isBind) {
        this.isBind = isBind;
    }

    public Integer getbOrderNums() {
        return bOrderNums;
    }

    public void setbOrderNums(Integer bOrderNums) {
        this.bOrderNums = bOrderNums;
    }

    public Integer getPartnerNums() {
        return partnerNums;
    }

    public void setPartnerNums(Integer partnerNums) {
        this.partnerNums = partnerNums;
    }

    public String getTotalIncomeFee() {
        return totalIncomeFee;
    }

    public void setTotalIncomeFee(String totalIncomeFee) {
        this.totalIncomeFee = totalIncomeFee;
    }

    public String getProfitFee() {
        return profitFee;
    }

    public void setProfitFee(String profitFee) {
        this.profitFee = profitFee;
    }

    public String getNkName() {
        return nkName;
    }

    public void setNkName(String nkName) {
        this.nkName = nkName;
    }

    public String getUserHeadImg() {
        return userHeadImg;
    }

    public void setUserHeadImg(String userHeadImg) {
        this.userHeadImg = userHeadImg;
    }

    public String getImgURl() {
        return imgURl;
    }

    public void setImgURl(String imgURl) {
        this.imgURl = imgURl;
    }

    public Integer getCountMsg() {
        return countMsg;
    }

    public void setCountMsg(Integer countMsg) {
        this.countMsg = countMsg;
    }

    public Integer getUpgradeNum() {
        return upgradeNum;
    }

    public void setUpgradeNum(Integer upgradeNum) {
        this.upgradeNum = upgradeNum;
    }

    public List<PfUserOrganization> getPfUserOrganizations() {
        return pfUserOrganizations;
    }

    public void setPfUserOrganizations(List<PfUserOrganization> pfUserOrganizations) {
        this.pfUserOrganizations = pfUserOrganizations;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Integer getBrandNum() {
        return brandNum;
    }

    public void setBrandNum(Integer brandNum) {
        this.brandNum = brandNum;
    }
}
