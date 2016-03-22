package com.masiis.shop.dao.beans.user;

import com.masiis.shop.dao.po.ComAgentLevel;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfUserSku;

/**
 * Created by 49134 on 2016/3/9.
 */
public class PfUserSkuCertificate extends PfUserSku {
    private ComUser comUser;
    private String skuName;
    private ComAgentLevel comAgentLevel;
    private String pRealName;
    private Integer lowerCount;
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Integer getLowerCount() {
        return lowerCount;
    }

    public void setLowerCount(Integer lowerCount) {
        this.lowerCount = lowerCount;
    }

    public ComUser getComUser() {
        return comUser;
    }

    public void setComUser(ComUser comUser) {
        this.comUser = comUser;
    }

    public String getSku_name() {
        return skuName;
    }

    public void setSku_name(String sku_name) {
        this.skuName = sku_name;
    }

    public ComAgentLevel getComAgentLevel() {
        return comAgentLevel;
    }

    public void setComAgentLevel(ComAgentLevel comAgentLevel) {
        this.comAgentLevel = comAgentLevel;
    }

    public String getpRealName() {
        return pRealName;
    }

    public void setpRealName(String pRealName) {
        this.pRealName = pRealName;
    }


}
