package com.masiis.shop.api.utils;

import com.masiis.shop.api.bean.common.CommonReq;
import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.api.bean.shop.DistributionRecordReq;
import com.masiis.shop.api.bean.user.AccountUserIncomePersonReq;
import com.masiis.shop.api.bean.user.AccountUserIncomeReq;
import com.masiis.shop.common.util.HttpClientUtils;

/**
 * Created by wangbingjian on 2016/8/8.
 */
public class HttpTest {

    public static void main(String[] args){

        String url = "http://localhost:8084/api/account/getIncomRecord14Person.do";
        AccountUserIncomePersonReq req = new AccountUserIncomePersonReq();
        req.setToken("EE8EE1FC8A848D8C821B180D6F49F9F37C935807");
        req.setDate("2016-07");
        req.setPageNum(1);
        req.setUserId(581l);
        String json = JSONObject.toJSON(req).toString();
        String message = HttpClientUtils.httpPost(url, json);
        System.out.println(message);
    }

}
