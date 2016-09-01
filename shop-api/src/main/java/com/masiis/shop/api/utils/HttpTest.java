package com.masiis.shop.api.utils;

import com.masiis.shop.api.bean.common.CommonReq;
import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.api.bean.shop.DistributionRecordReq;
import com.masiis.shop.common.util.HttpClientUtils;

/**
 * Created by wangbingjian on 2016/8/8.
 */
public class HttpTest {

    public static void main(String[] args){

        String url = "http://localhost:8084/distribution/getRecordByMonth.do";
        DistributionRecordReq distributionRecordReq = new DistributionRecordReq();
        distributionRecordReq.setToken("EE8EE1FC8A848D8C821B180D6F49F9F37C935807");
        distributionRecordReq.setDate("2016-05");
        distributionRecordReq.setPageNum(1);
        String json = JSONObject.toJSON(distributionRecordReq).toString();
        String message = HttpClientUtils.httpPost(url, json);
        System.out.println(message);
    }

}
