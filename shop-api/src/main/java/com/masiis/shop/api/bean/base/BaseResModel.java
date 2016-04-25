package com.masiis.shop.api.bean.base;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by wangbingjian on 2016/4/22.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class BaseResModel {
    /**
     * 返回状态结果码 0为成功 其他各业务自定义
     */
    private String resCode;
    private String resMsg;

    public BaseResModel(){}

    public BaseResModel(String resCode, String resMsg) {
        this.resCode = resCode;
        this.resMsg = resMsg;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }
}
