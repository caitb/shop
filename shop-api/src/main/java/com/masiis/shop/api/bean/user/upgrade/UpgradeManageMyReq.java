package com.masiis.shop.api.bean.user.upgrade;

import com.masiis.shop.api.bean.base.BaseReq;
import com.masiis.shop.api.bean.base.BaseRes;
import com.masiis.shop.dao.beans.user.upgrade.UpgradeManagePo;

/**
 * 升级管理-我的申请Po
 * Created by wangbingjian on 2016/8/3.
 */
public class UpgradeManageMyReq extends BaseReq{

    private String token;

    private Integer pageNum;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }
}
