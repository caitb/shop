package com.masiis.shop.api.bean.user.upgrade;

import com.masiis.shop.api.bean.base.BaseBusinessRes;

/**
 * Created by wangbingjian on 2016/8/5.
 */
public class UpgradeConfirmRes extends BaseBusinessRes {
    /**
     * 升级单id
     */
    private Long keyProperty;
    /**
     * 跳转状态  1：跳转订单界面   2：跳转申请完成页面
     */
    private Integer status;

    public Long getKeyProperty() {
        return keyProperty;
    }

    public void setKeyProperty(Long keyProperty) {
        this.keyProperty = keyProperty;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
