package com.masiis.shop.api.bean.base;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by wangbingjian on 2016/4/22.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class BaseResponseModel {

    /**
     * 返回状态结果码 0为成功 其他各业务自定义
     */
    private int code;
    private String msg = "";

    public BaseResponseModel(int code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public BaseResponseModel() {
        super();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getTime(){
        return System.currentTimeMillis();
    }
}
