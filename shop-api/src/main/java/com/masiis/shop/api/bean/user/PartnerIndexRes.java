package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BaseRes;

import java.util.List;

/**
 * @Date 2016/5/3
 * @Auther lzh
 */
public class PartnerIndexRes extends BaseRes {
    private Integer isPartner;
    private Integer isBind;
    private Integer bOrderNums;
    private Integer partnerNums;
    private String totalIncomeFee;
    private String profitFee;
    private String nkName;
    private String userHeadImg;
    private List<String> imgs;

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

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }
}
