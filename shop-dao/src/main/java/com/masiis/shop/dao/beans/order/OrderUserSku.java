package com.masiis.shop.dao.beans.order;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * @author muchaofeng
 *用户商品订单详情
 * @date $date$ $time$
 */

public class OrderUserSku {
    /**
     * id
     */
    private Integer orderUserSkuId;
    /**
     * 商品名称
     */
    private List<String> skuName;
    /**
     * 用户名称
     */
    private  String userName;
    /**
     * 代理等级
     */
    private String agentLevel;
    /**
     * 上级代理人名称
     */
    private String superiorName;

    public void setAgentLevel(String agentLevel) {
        this.agentLevel = agentLevel;
    }

    public void setOrderUserSkuId(Integer orderUserSkuId) {
        this.orderUserSkuId = orderUserSkuId;
    }

    public void setSuperiorName(String superiorName) {
        this.superiorName = superiorName;
    }

    public Integer getOrderUserSkuId() {
        return orderUserSkuId;
    }

    public String getAgentLevel() {
        return agentLevel;
    }

    public String getSuperiorName() {
        return superiorName;
    }

    public void setSkuName(List<String> skuName) {this.skuName = skuName;}
    public String getUserName() {return userName;}

    public List<String> getSkuName() {return skuName;}

    public void setUserName(String userName) {this.userName = userName;}
}
