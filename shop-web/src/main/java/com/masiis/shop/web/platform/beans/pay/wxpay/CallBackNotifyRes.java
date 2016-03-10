package com.masiis.shop.web.platform.beans.pay.wxpay;

/**
 * 统一下单异步回调通知返回参数对象
 *
 * Created by lzh on 2016/3/10.
 */
public class CallBackNotifyRes {
    private String return_code;
    private String return_msg;

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
}
