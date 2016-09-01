package com.masiis.shop.api.bean.user.upgrade;

import com.masiis.shop.api.bean.base.BaseBusinessRes;

/**
 * Created by wangbingjian on 2016/8/9.
 */
public class TemporarilyRes extends BaseBusinessRes {

    private Integer status;

    private String statusView;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusView() {
        return statusView;
    }

    public void setStatusView(String statusView) {
        this.statusView = statusView;
    }
}
