package com.masiis.shop.api.bean.order;

import com.masiis.shop.api.bean.base.BaseRes;
import com.masiis.shop.dao.po.PfBorder;
import com.masiis.shop.dao.po.PfBorderItem;

import java.util.List;
import java.util.Map;

/**
 * Created by hw on 2016/8/8.
 */
public class PfBorderCashierdeskRes extends BaseRes {

    private PfBorder pfBorder;
    private List<PfBorderItem> pfBorderItems;
    private String downPayLatestTime;
    private Map<String, Object> agentInfo;

    public Map<String, Object> getAgentInfo() {
        return agentInfo;
    }

    public void setAgentInfo(Map<String, Object> agentInfo) {
        this.agentInfo = agentInfo;
    }

    public String getDownPayLatestTime() {
        return downPayLatestTime;
    }

    public void setDownPayLatestTime(String downPayLatestTime) {
        this.downPayLatestTime = downPayLatestTime;
    }

    public PfBorder getPfBorder() {
        return pfBorder;
    }

    public void setPfBorder(PfBorder pfBorder) {
        this.pfBorder = pfBorder;
    }

    public List<PfBorderItem> getPfBorderItems() {
        return pfBorderItems;
    }

    public void setPfBorderItems(List<PfBorderItem> pfBorderItems) {
        this.pfBorderItems = pfBorderItems;
    }
}
