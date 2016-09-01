package com.masiis.shop.api.bean.pay;

import com.masiis.shop.api.bean.base.BaseBusinessRes;
import com.masiis.shop.common.beans.wx.wxpay.WxAPPBrandWCPayReq;

/**
 * @Date 2016/5/20
 * @Auther lzh
 */
public class WxPayPrepareBOrderRes extends BaseBusinessRes {
    private WxAPPBrandWCPayReq paramReq;

    public WxAPPBrandWCPayReq getParamReq() {
        return paramReq;
    }

    public void setParamReq(WxAPPBrandWCPayReq paramReq) {
        this.paramReq = paramReq;
    }
}
