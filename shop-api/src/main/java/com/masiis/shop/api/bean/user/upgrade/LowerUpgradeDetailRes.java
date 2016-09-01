package com.masiis.shop.api.bean.user.upgrade;

import com.masiis.shop.api.bean.base.BaseBusinessRes;
import com.masiis.shop.dao.beans.user.upgrade.UpGradeInfoPo;

/**
 * 展示下级申请详情页
 * Created by wangbingjian on 2016/8/4.
 */
public class LowerUpgradeDetailRes extends BaseBusinessRes {
    /**
     * 页面展示po
     */
    private UpGradeInfoPo upGradeInfoPo;
    /**
     * 申请时间
     */
    private String applyTime;
    /**
     * 逾期处理时间
     */
    private String overdueDate;
    /**
     * 申请状态展示
     */
    private String applyStatusView;

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

    public String getOverdueDate() {
        return overdueDate;
    }

    public void setOverdueDate(String overdueDate) {
        this.overdueDate = overdueDate;
    }

    public String getApplyStatusView() {
        return applyStatusView;
    }

    public void setApplyStatusView(String applyStatusView) {
        this.applyStatusView = applyStatusView;
    }
}
