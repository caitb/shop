package com.masiis.shop.dao.beans.promotion;

import com.masiis.shop.dao.po.SfTurnTable;
import com.masiis.shop.dao.po.SfUserTurnTable;

import java.util.List;

/**
 * Created by hzz on 2016/8/1.
 */
public class TurnTablelInfo extends SfTurnTable{

    private Integer turnTableId;
    private List<TurnTableGiftInfo> turnTableGiftInfo;
    private String beginTimeString;
    private String endTimeString;

    public Integer getTurnTableId() {
        return turnTableId;
    }

    public void setTurnTableId(Integer turnTableId) {
        this.turnTableId = turnTableId;
    }

    public List<TurnTableGiftInfo> getTurnTableGiftInfo() {
        return turnTableGiftInfo;
    }

    public void setTurnTableGiftInfo(List<TurnTableGiftInfo> turnTableGiftInfo) {
        this.turnTableGiftInfo = turnTableGiftInfo;
    }

    public String getBeginTimeString() {
        return beginTimeString;
    }

    public void setBeginTimeString(String beginTimeString) {
        this.beginTimeString = beginTimeString;
    }

    public String getEndTimeString() {
        return endTimeString;
    }

    public void setEndTimeString(String endTimeString) {
        this.endTimeString = endTimeString;
    }
}
