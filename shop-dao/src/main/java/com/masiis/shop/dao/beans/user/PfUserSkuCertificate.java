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
