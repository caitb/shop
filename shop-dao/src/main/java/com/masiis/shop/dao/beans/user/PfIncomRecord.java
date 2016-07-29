package com.masiis.shop.dao.beans.user;

import com.masiis.shop.common.enums.platform.BOrderType;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * B端收入记录PO
 * Created by wangbingjian on 2016/7/27.
 */
public class PfIncomRecord {
    /**
     * 订单id
     */
    private Long orderId;
    /**
     * 是否为推荐奖励  为2时为推荐奖励
     */
    private Integer recommenType;
    /**
     * 订单类型
     */
    private Integer orderType;
    /**
     * 订单类型页面展示
     */
    private String orderTypeView;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户名称
     */
    private String realName;
    /**
     * 用户头像
     */
    private String headImg;
    /**
     * 商品id
     */
    private Integer skuId;
    /**
     * 商品名称
     */
    private String skuName;
    /**
     * B/C端标识   1：B端  2：C端
     */
    private Integer flag;
    /**
     * 收入
     */
    private BigDecimal inCome;
    /**
     * 收入显示
     */
    private String inComView;
    /**
     * 生成时间
     */
    private Date createTime;

    private String viewType;

    private String yearView;

    private String minView;

    private static final SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy/MM/dd");

    private static final SimpleDateFormat sdfMin = new SimpleDateFormat("HH:mm:ss");

    private static final NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.CHINA);

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getRecommenType() {
        return recommenType;
    }

    public void setRecommenType(Integer recommenType) {
        this.recommenType = recommenType;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getOrderTypeView() {
        return orderTypeView;
    }

    public void setOrderTypeView(String orderTypeView) {
        this.orderTypeView = orderTypeView;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public BigDecimal getInCome() {
        return inCome;
    }

    public void setInCome(BigDecimal inCome) {
        this.inCome = inCome;
        setInComView(numberFormat.format(inCome));
    }

    public String getInComView() {
        return inComView;
    }

    public void setInComView(String inComView) {
        this.inComView = inComView;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
        setYearView(sdfYear.format(createTime));
        setMinView(sdfMin.format(createTime));
    }

    public String getYearView() {
        return yearView;
    }

    public void setYearView(String yearView) {
        this.yearView = yearView;
    }

    public String getMinView() {
        return minView;
    }

    public void setMinView(String minView) {
        this.minView = minView;
    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
        String[] args = viewType.split(",");
        Integer flag = Integer.valueOf(args[2]);
        Integer orderType = Integer.valueOf(args[1]);
        Integer recommenType = Integer.valueOf(args[0]);
        switch (flag.intValue()){
            case 1 : {
                if (recommenType.intValue() == 2){
                    setOrderTypeView("推荐奖励");
                }else {
                    setOrderTypeView(BOrderType.getByCode(orderType).getDesc());
                }
                break;
            }
            case 2 : {
                setOrderTypeView("店铺订单");
            }
        }
    }
}
