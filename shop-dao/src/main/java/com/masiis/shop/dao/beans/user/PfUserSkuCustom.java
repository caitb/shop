package com.masiis.shop.dao.beans.user;

import com.masiis.shop.dao.po.PfUserSku;

import java.util.Date;

/**
 * PfUserSkuCustom
 *
 * @author ZhaoLiang
 * @date 2016/4/6
 */
public class PfUserSkuCustom extends PfUserSku {
    /**
     * spu主键id
     */
    private Integer spuId;
    /**
     * 身份证号码
     */
    private String idCard;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 微信号
     */
    private String wxId;

    public Integer getSpuId() {
        return spuId;
    }

    public void setSpuId(Integer spuId) {
        this.spuId = spuId;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
    }
}
