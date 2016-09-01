package com.masiis.shop.dao.beans.message;

/**
 * @Date 2016/8/29
 * @Author lzh
 */
public class PfMessageToNewBean {
    private String oName;
    private String oUrl;
    private Integer oNum;
    private Integer pfUserOrganizationId;

    public String getoName() {
        return oName;
    }

    public void setoName(String oName) {
        this.oName = oName;
    }

    public String getoUrl() {
        return oUrl;
    }

    public void setoUrl(String oUrl) {
        this.oUrl = oUrl;
    }

    public Integer getoNum() {
        return oNum;
    }

    public void setoNum(Integer oNum) {
        this.oNum = oNum;
    }

    public Integer getPfUserOrganizationId() {
        return pfUserOrganizationId;
    }

    public void setPfUserOrganizationId(Integer pfUserOrganizationId) {
        this.pfUserOrganizationId = pfUserOrganizationId;
    }
}
