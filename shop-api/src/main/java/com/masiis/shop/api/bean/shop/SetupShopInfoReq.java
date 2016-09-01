package com.masiis.shop.api.bean.shop;

import com.masiis.shop.api.bean.base.BasePagingReq;


public class SetupShopInfoReq extends BasePagingReq {

    private String name;
    private String explanation;
    private String wxQrCodeDescription;
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getWxQrCodeDescription() {
        return wxQrCodeDescription;
    }

    public void setWxQrCodeDescription(String wxQrCodeDescription) {
        this.wxQrCodeDescription = wxQrCodeDescription;
    }

    @Override
    public String toString() {
        return "SetupShopInfoReq{" +
                "name='" + name + '\'' +
                ", explanation='" + explanation + '\'' +
                ", wxQrCodeDescription='" + wxQrCodeDescription + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
