package com.masiis.shop.api.bean.order;

import com.masiis.shop.api.bean.base.BaseBusinessRes;
import com.masiis.shop.dao.beans.user.AgentSkuViewInfo;
import com.masiis.shop.dao.po.ComBrand;

import java.util.List;

/**
 * @Date 2016/8/8
 * @Author lzh
 */
public class BAgentConfirmRes extends BaseBusinessRes {
    private String fName;
    private String fGeneral;
    private String fUrl;
    /**
     * 如果指定,上级姓名
     */
    private String pName;
    /**
     * 指定品牌主打商品
     */
    private ComBrand brand;
    /**
     * 等级价格信息
     */
    private AgentSkuViewInfo view;
    /**
     * 附带可以添加的商品
     */
    private List<AgentSkuViewInfo> others;

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getfGeneral() {
        return fGeneral;
    }

    public void setfGeneral(String fGeneral) {
        this.fGeneral = fGeneral;
    }

    public String getfUrl() {
        return fUrl;
    }

    public void setfUrl(String fUrl) {
        this.fUrl = fUrl;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public ComBrand getBrand() {
        return brand;
    }

    public void setBrand(ComBrand brand) {
        this.brand = brand;
    }

    public AgentSkuViewInfo getView() {
        return view;
    }

    public void setView(AgentSkuViewInfo view) {
        this.view = view;
    }

    public List<AgentSkuViewInfo> getOthers() {
        return others;
    }

    public void setOthers(List<AgentSkuViewInfo> others) {
        this.others = others;
    }
}
