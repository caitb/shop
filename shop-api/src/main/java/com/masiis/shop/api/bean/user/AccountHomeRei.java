package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BaseRes;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by wl on 2016/8/8.
 */
public class AccountHomeRei extends BaseRes {
    /**
     * 用户头像
     */
    private String wxHeadImg;
    /**
     * 用户在平台的昵称,不同于微信昵称
     */
    private String wxNkName;
    /**
     * 可提现金额
     */
    private BigDecimal extractableFee;
    /**
     * 申请中金额
     */
    private String appliedFee;
    /**
     * 已提现
     */
    private String withdrawd;
    /**
     * 结算中金额
     */
    private BigDecimal countingFee;
    /**
     * 累积收入
     */
    private String totalIncom;
    /**
     * 代理结算中
     */
    private BigDecimal agentBillAmount;
    /**
     * 分销结算中
     */
    private BigDecimal distributionBillAmount;
    /**
     * 推荐奖励结算中
     */
    private BigDecimal recommenBillAmount;
    /**
     * 等级图片路径
     */
    private List<String> imgUrl;

    public List<String> getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(List<String> imgUrl) {
        this.imgUrl = imgUrl;
    }

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getWxHeadImg() {
        return wxHeadImg;
    }
    public void setWxHeadImg(String wxHeadImg) {
        this.wxHeadImg = wxHeadImg;
    }
    public String getWxNkName() {
        return wxNkName;
    }
    public void setWxNkName(String wxNkName) {
        this.wxNkName = wxNkName;
    }

    public String getTotalIncom() {
        return totalIncom;
    }

    public void setTotalIncom(String totalIncom) {
        this.totalIncom = totalIncom;
    }

    public BigDecimal getExtractableFee() {
        return extractableFee;
    }

    public void setExtractableFee(BigDecimal extractableFee) {
        this.extractableFee = extractableFee;
    }

    public String getAppliedFee() {
        return appliedFee;
    }

    public void setAppliedFee(String appliedFee) {
        this.appliedFee = appliedFee;
    }

    public String getWithdrawd() {
        return withdrawd;
    }

    public void setWithdrawd(String withdrawd) {
        this.withdrawd = withdrawd;
    }

    public BigDecimal getCountingFee() {
        return countingFee;
    }

    public void setCountingFee(BigDecimal countingFee) {
        this.countingFee = countingFee;
    }

    public BigDecimal getAgentBillAmount() {
        return agentBillAmount;
    }

    public void setAgentBillAmount(BigDecimal agentBillAmount) {
        this.agentBillAmount = agentBillAmount;
    }

    public BigDecimal getDistributionBillAmount() {
        return distributionBillAmount;
    }

    public void setDistributionBillAmount(BigDecimal distributionBillAmount) {
        this.distributionBillAmount = distributionBillAmount;
    }

    public BigDecimal getRecommenBillAmount() {
        return recommenBillAmount;
    }

    public void setRecommenBillAmount(BigDecimal recommenBillAmount) {
        this.recommenBillAmount = recommenBillAmount;
    }
}
