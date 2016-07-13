package com.masiis.shop.dao.beans.recommend;


import java.math.BigDecimal;

/**
 * Created by cai_tb on 16/6/15.
 */
public class RecommendStatistics {

    /* 证书编号 */
    private String code;
    /* 姓名 */
    private String realName;
    /* 商品名 */
    private String skuName;
    /* 我推荐的人数 */
    private Integer countRecommend;
    /* 推荐我的人数 */
    private Integer countPresentee;
    /* 获取的奖励 */
    private BigDecimal recommenGetFee;
    /* 发出的奖励 */
    private BigDecimal recommenSendFee;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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

    public Integer getCountRecommend() {
        return countRecommend;
    }

    public void setCountRecommend(Integer countRecommend) {
        this.countRecommend = countRecommend;
    }

    public Integer getCountPresentee() {
        return countPresentee;
    }

    public void setCountPresentee(Integer countPresentee) {
        this.countPresentee = countPresentee;
    }

    public BigDecimal getRecommenGetFee() {
        return recommenGetFee;
    }

    public void setRecommenGetFee(BigDecimal recommenGetFee) {
        this.recommenGetFee = recommenGetFee;
    }

    public BigDecimal getRecommenSendFee() {
        return recommenSendFee;
    }

    public void setRecommenSendFee(BigDecimal recommenSendFee) {
        this.recommenSendFee = recommenSendFee;
    }

    @Override
    public String toString() {
        return "RecommendStatistics{" +
                "code='" + code + '\'' +
                ", realName='" + realName + '\'' +
                ", skuName='" + skuName + '\'' +
                ", countRecommend=" + countRecommend +
                ", countPresentee=" + countPresentee +
                ", recommenGetFee=" + recommenGetFee +
                ", recommenSendFee=" + recommenSendFee +
                '}';
    }
}
