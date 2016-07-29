package com.masiis.shop.dao.beans.recommend;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * 我的推荐页面展示PO
 * Created by wangbingjian on 2016/7/28.
 */
public class MyRecommendPo {
    /**
     * 我推荐的人
     */
    private Integer myRecommedPeople;
    /**
     * 推荐团队总数
     */
    private Integer recommenTeamCount;
    /**
     * 总销售额
     */
    private BigDecimal totalSales;
    /**
     * 总销售额页面展示
     */
    private String totalSalesView;
    /**
     * 获得奖励
     */
    private BigDecimal incomeRewards;
    /**
     * 获得奖励页面展示
     */
    private String incomeRewardsView;
    /**
     * 发出奖励
     */
    private BigDecimal sendRewards;
    /**
     * 发出奖励页面展示
     */
    private String sendRewardsView;
    /**
     * 总数
     */
    private Long totalCount;
    /**
     * 当前页码
     */
    private Integer currentPage;
    /**
     * 订单列表
     */
    List<RecommenOrder> recommenOrders;

    private static final NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.CHINA);

    public Integer getMyRecommedPeople() {
        return myRecommedPeople;
    }

    public void setMyRecommedPeople(Integer myRecommedPeople) {
        this.myRecommedPeople = myRecommedPeople;
    }

    public Integer getRecommenTeamCount() {
        return recommenTeamCount;
    }

    public void setRecommenTeamCount(Integer recommenTeamCount) {
        this.recommenTeamCount = recommenTeamCount;
    }

    public BigDecimal getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(BigDecimal totalSales) {
        this.totalSales = totalSales;
        setTotalSalesView(numberFormat.format(totalSales));
    }

    public String getTotalSalesView() {
        return totalSalesView;
    }

    public void setTotalSalesView(String totalSalesView) {
        this.totalSalesView = totalSalesView;
    }

    public BigDecimal getIncomeRewards() {
        return incomeRewards;
    }

    public void setIncomeRewards(BigDecimal incomeRewards) {
        this.incomeRewards = incomeRewards;
        setIncomeRewardsView(numberFormat.format(incomeRewards));
    }

    public String getIncomeRewardsView() {
        return incomeRewardsView;
    }

    public void setIncomeRewardsView(String incomeRewardsView) {
        this.incomeRewardsView = incomeRewardsView;
    }

    public BigDecimal getSendRewards() {
        return sendRewards;
    }

    public void setSendRewards(BigDecimal sendRewards) {
        this.sendRewards = sendRewards;
        setSendRewardsView(numberFormat.format(sendRewards));
    }

    public String getSendRewardsView() {
        return sendRewardsView;
    }

    public void setSendRewardsView(String sendRewardsView) {
        this.sendRewardsView = sendRewardsView;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public List<RecommenOrder> getRecommenOrders() {
        return recommenOrders;
    }

    public void setRecommenOrders(List<RecommenOrder> recommenOrders) {
        this.recommenOrders = recommenOrders;
    }
}
