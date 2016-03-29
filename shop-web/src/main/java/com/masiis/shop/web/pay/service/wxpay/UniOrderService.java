package com.masiis.shop.web.pay.service.wxpay;

import com.masiis.shop.web.platform.beans.pay.wxpay.Configure;
import com.masiis.shop.web.platform.beans.pay.wxpay.UnifiedOrderReq;

/**
 * Created by lzh on 2016/3/9.
 */
public class UniOrderService extends BaseService{
    public UniOrderService() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        super(Configure.H5_PAY_API);
    }

    /**
     * 请求支付服务
     * @param req 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的数据
     * @throws Exception
     */
    public String request(UnifiedOrderReq req) throws Exception {

        //--------------------------------------------------------------------
        //发送HTTPS的Post请求到API地址
        //--------------------------------------------------------------------
        String responseString = sendPost(req);

        return responseString;
    }
}
