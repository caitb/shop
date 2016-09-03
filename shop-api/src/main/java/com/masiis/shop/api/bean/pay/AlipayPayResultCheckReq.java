package com.masiis.shop.api.bean.pay;

import com.masiis.shop.api.bean.base.BaseBusinessReq;
import com.masiis.shop.common.beans.tb.alipay.AlipayTradeAppPayRes;

/**
 * @Date 2016/9/2
 * @Author lzh
 */
public class AlipayPayResultCheckReq extends BaseBusinessReq {
    private String memo;
    private String result;
    private String resultStatus;

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }
}
