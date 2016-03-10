package com.masiis.shop.web.platform.service.pay.wxpay;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lzh on 2016/3/10.
 */
@Service
public class CallBackNotifyService {
    private Logger log = Logger.getLogger(this.getClass());

    @Transactional
    public void uniOrderNotify(){

    }
}
