package com.masiis.shop.common.beans.wx.wxpay;

import com.alibaba.fastjson.annotation.JSONField;
import com.masiis.shop.common.annotation.SignField;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @Date 2016/8/12
 * @Author lzh
 */
@XStreamAlias("xml")
public class WxAPPBrandWCPayReq {
    /**
     * 应用appID
     */
    private String appid;
    /**
     * 微信支付分配的商户号
     */
    private String partnerid;
    /**
     * 预支付交易会话ID,微信返回的支付交易会话ID
     */
    private String prepayid;
    /**
     * 扩展字段,暂填写固定值Sign=WXPay
     */
    @JSONField(name = "package")
    @XStreamAlias("package")
    private String packages;
    /**
     * 随机字符串,不长于32位。推荐随机数生成算法
     */
    private String noncestr;
    /**
     * 时时间戳，请见接口规则-参数规定
     */
    private String timestamp;
    /**
     * 签名
     */
    @SignField
    private String sign;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "WxAPPBrandWCPayReq{" +
                "appid='" + appid + '\'' +
                ", partnerid='" + partnerid + '\'' +
                ", prepayid='" + prepayid + '\'' +
                ", packages='" + packages + '\'' +
                ", noncestr='" + noncestr + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
