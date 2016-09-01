package com.masiis.shop.api.bean.user.upgrade;

import com.masiis.shop.api.bean.base.BaseBusinessRes;
import com.masiis.shop.dao.beans.user.upgrade.UpGradeInfoPo;

/**
 * 展示下级申请详情页
 * Created by wangbingjian on 2016/8/4.
 */
public class OneRebateUpgradeDetailRes extends BaseBusinessRes {
    /**
     * 页面展示po
     */
    private UpGradeInfoPo upGradeInfoPo;
    /**
     * 申请时间
     */
    private String applyTime;
    /**
     * 新上级姓名
     */
    private String nuwUpName;
    /**
     * 申请状态展示
     */
    private String applyStatusView;
    /**
     * 1:收到奖励   2：发出奖励
     */
    private Integer income;

    public UpGradeInfoPo getUpGradeInfoPo() {
        return upGradeInfoPo;
    }

    public void setUpGradeInfoPo(UpGradeInfoPo upGradeInfoPo) {
        this.upGradeInfoPo = upGradeInfoPo;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getNuwUpName() {
        return nuwUpName;
    }

    public void setNuwUpName(String nuwUpName) {
        this.nuwUpName = nuwUpName;
    }

    public String getApplyStatusView() {
        return applyStatusView;
    }

    public void setApplyStatusView(String applyStatusView) {
        this.applyStatusView = applyStatusView;
    }

    public Integer getIncome() {
        return income;
    }

    public void setIncome(Integer income) {
        this.income = income;
    }
}
