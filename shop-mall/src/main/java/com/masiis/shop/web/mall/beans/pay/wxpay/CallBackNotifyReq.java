package com.masiis.shop.web.mall.beans.pay.wxpay;

import com.masiis.shop.common.annotation.SignField;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

/**
 * 支付结果通用通知bean
 *
 * Created by lzh on 2016/3/10.
 */
@XStreamAlias("xml")
public class CallBackNotifyReq {
    /**
     * 此字段是通信标识,非交易标识,交易是否成功需要查看result_code来判断
     */
    private String return_code;
    /**
     * 返回信息,为错误原因
     */
    private String return_msg;

    //以下字段在return_code为SUCCESS的时候有返回

    /**
     * 公众账号ID,微信分配的公众账号ID（企业号corpid即为此appId）
     */
    private String appid;
    /**
     * 商户号,微信支付分配的商户号
     */
    private String mch_id;
    /**
     * 设备号,微信支付分配的终端设备号
     */
    private String device_info;
    /**
     * 随机字符串,不长于32位
     */
    private String nonce_str;
    /**
     * 签名
     */
    @SignField
    private String sign;
    /**
     * 业务结果
     */
    private String result_code;
    /**
     * 错误代码
     */
    private String err_code;
    /**
     * 错误代码描述
     */
    private String err_code_des;
    /**
     * 用户在商户appid下的唯一标识
     */
    private String openid;
    /**
     * 是否关注公众账号,Y-关注，N-未关注,仅在公众账号类型支付有效
     */
    private String is_subscribe;
    /**
     * 交易类型,JSAPI、NATIVE、APP
     */
    private String trade_type;
    /**
     * 付款银行,采用字符串类型的银行标识,银行类型见银行列表
     */
    private String bank_type;
    /**
     * 总金额,订单总金额,单位为分
     */
    private String total_fee;
    /**
     * 货币种类,符合ISO4217标准的三位字母代码,默认人民币:CNY
     */
    private String fee_type;
    /**
     * 现金支付金额,现金支付金额订单现金支付金额,详见支付金额
     */
    private String cash_fee;
    /**
     * 现金支付货币类型,默认人民币:CNY,其他值列表详见货币类型
     */
    private String cash_fee_type;
    /**
     * 代金券或立减优惠金额,代金券或立减优惠金额<=订单总金额,订单总金额-代金券或立减优惠金额=现金支付金额
     */
    private String coupon_fee;
    /**
     * 代金券或立减优惠使用数量
     */
    private String coupon_count;
    /**
     * 代金券或立减优惠ID,$n为下标，从0开始编号
     */
    private String coupon_id_$n;
    /**
     * 单个代金券或立减优惠支付金额,$n为下标，从0开始编号
     */
    private String coupon_fee_$n;
    /**
     * 微信支付订单号
     */
    private String transaction_id;
    /**
     * 商户订单号,与请求一致(我们的是支付流水号)
     */
    private String out_trade_no;
    /**
     * 商家数据包,商家数据包，原样返回
     */
    private String attach;
    /**
     * 支付完成时间
     */
    private String time_end;
    /**
     * 子商户号,文档没有,举例有,先加上了
     */
    private String sub_mch_id;

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getErr_code() {
        return err_code;
    }

    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    public String getErr_code_des() {
        return err_code_des;
    }

    public void setErr_code_des(String err_code_des) {
        this.err_code_des = err_code_des;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getIs_subscribe() {
        return is_subscribe;
    }

    public void setIs_subscribe(String is_subscribe) {
        this.is_subscribe = is_subscribe;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getBank_type() {
        return bank_type;
    }

    public void setBank_type(String bank_type) {
        this.bank_type = bank_type;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public String getCash_fee() {
        return cash_fee;
    }

    public void setCash_fee(String cash_fee) {
        this.cash_fee = cash_fee;
    }

    public String getCash_fee_type() {
        return cash_fee_type;
    }

    public void setCash_fee_type(String cash_fee_type) {
        this.cash_fee_type = cash_fee_type;
    }

    public String getCoupon_fee() {
        return coupon_fee;
    }

    public void setCoupon_fee(String coupon_fee) {
        this.coupon_fee = coupon_fee;
    }

    public String getCoupon_count() {
        return coupon_count;
    }

    public void setCoupon_count(String coupon_count) {
        this.coupon_count = coupon_count;
    }

    public String getCoupon_id_$n() {
        return coupon_id_$n;
    }

    public void setCoupon_id_$n(String coupon_id_$n) {
        this.coupon_id_$n = coupon_id_$n;
    }

    public String getCoupon_fee_$n() {
        return coupon_fee_$n;
    }

    public void setCoupon_fee_$n(String coupon_fee_$n) {
        this.coupon_fee_$n = coupon_fee_$n;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public String getSub_mch_id() {
        return sub_mch_id;
    }

    public void setSub_mch_id(String sub_mch_id) {
        this.sub_mch_id = sub_mch_id;
    }

    public static void main(String[] args){
        String xml = "<xml>\n" +
                "  <appid><![CDATA[wx2421b1c4370ec43b]]></appid>\n" +
                "  <attach><![CDATA[支付测试]]></attach>\n" +
                "  <bank_type><![CDATA[CFT]]></bank_type>\n" +
                "  <fee_type><![CDATA[CNY]]></fee_type>\n" +
                "  <is_subscribe><![CDATA[Y]]></is_subscribe>\n" +
                "  <mch_id><![CDATA[10000100]]></mch_id>\n" +
                "  <nonce_str><![CDATA[5d2b6c2a8db53831f7eda20af46e531c]]></nonce_str>\n" +
                "  <openid><![CDATA[oUpF8uMEb4qRXf22hE3X68TekukE]]></openid>\n" +
                "  <out_trade_no><![CDATA[1409811653]]></out_trade_no>\n" +
                "  <result_code><![CDATA[SUCCESS]]></result_code>\n" +
                "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "  <sign><![CDATA[B552ED6B279343CB493C5DD0D78AB241]]></sign>\n" +
                /*"  <sub_mch_id><![CDATA[10000100]]></sub_mch_id>\n" +*/
                "  <time_end><![CDATA[20140903131540]]></time_end>\n" +
                "  <total_fee>1</total_fee>\n" +
                "  <trade_type><![CDATA[JSAPI]]></trade_type>\n" +
                "  <transaction_id><![CDATA[1004400740201409030005092168]]></transaction_id>\n" +
                "</xml>";
        XStream xStream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        xStream.ignoreUnknownElements();
        xStream.processAnnotations(CallBackNotifyReq.class);
        Object ss = xStream.fromXML(xml);
        CallBackNotifyReq res = (CallBackNotifyReq) ss;
    }
}
