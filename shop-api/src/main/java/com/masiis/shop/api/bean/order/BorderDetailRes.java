package com.masiis.shop.api.bean.order;

import com.masiis.shop.api.bean.base.BasePagingRes;
import com.masiis.shop.dao.beans.order.BorderDetail;
import com.masiis.shop.dao.mallBeans.OrderMallDetail;
import com.masiis.shop.dao.po.*;

import java.util.List;
import java.util.Map;

/**
 * @Date 2016/5/5
 * @Auther lzh
 */
public class BorderDetailRes extends BasePagingRes {

    private BorderDetail borderDetail;
    private OrderMallDetail orderMallDetail;
    private String stringBuffer;
    private Integer stockNum;
    private List<ComShipMan> comShipMans;
    private String imgUrlPrefix;
    private Map<String, Object> rewordInfo;//推荐信息
    private String paymentDesc;//支付方式
    private String skuImgUrl;
    private ComSkuImage defaultImg;//默认商品图片

    public ComSkuImage getDefaultImg() {
        return defaultImg;
    }

    public void setDefaultImg(ComSkuImage defaultImg) {
        this.defaultImg = defaultImg;
    }

    public String getSkuImgUrl() {
        return skuImgUrl;
    }

    public void setSkuImgUrl(String skuImgUrl) {
        this.skuImgUrl = skuImgUrl;
    }

    public String getPaymentDesc() {
        return paymentDesc;
    }

    public void setPaymentDesc(String paymentDesc) {
        this.paymentDesc = paymentDesc;
    }

    public Map<String, Object> getRewordInfo() {
        return rewordInfo;
    }

    public void setRewordInfo(Map<String, Object> rewordInfo) {
        this.rewordInfo = rewordInfo;
    }

    public String getImgUrlPrefix() {
        return imgUrlPrefix;
    }

    public void setImgUrlPrefix(String imgUrlPrefix) {
        this.imgUrlPrefix = imgUrlPrefix;
    }

    public void setStockNum(Integer stockNum) {
        this.stockNum = stockNum;
    }

    public void setOrderMallDetail(OrderMallDetail orderMallDetail) {
        this.orderMallDetail = orderMallDetail;
    }

    public OrderMallDetail getOrderMallDetail() {
        return orderMallDetail;
    }

    public Integer getStockNum() {return stockNum;}
    public void setStringBuffer(String stringBuffer) {this.stringBuffer = stringBuffer;}

    public String getStringBuffer() {return stringBuffer;}

    public void setComShipMans(List<ComShipMan> comShipMans) {
        this.comShipMans = comShipMans;
    }

    public List<ComShipMan> getComShipMans() {
        return comShipMans;
    }

    public void setBorderDetail(BorderDetail borderDetail) {
        this.borderDetail = borderDetail;
    }

    public BorderDetail getBorderDetail() {
        return borderDetail;
    }
}
