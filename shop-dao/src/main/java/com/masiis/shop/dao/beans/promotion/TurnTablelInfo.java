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
}
