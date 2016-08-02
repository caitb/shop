package com.masiis.shop.dao.beans.promotion;

import com.masiis.shop.dao.po.SfTurnTable;
import com.masiis.shop.dao.po.SfUserTurnTable;
import com.masiis.shop.dao.po.SfUserTurnTableRecord;

import java.util.List;
import java.util.Map;

/**
 * Created by hzz on 2016/8/1.
 */
public class TurnTablelInfo extends SfTurnTable{

    private Integer turnTableId;
    private List<TurnTableGiftInfo> turnTableGiftInfo;
    private List<SfUserTurnTableRecord> userTurnTableRecords;
    private Map<Integer,Integer> giftIdMap;//<序号,id>
    private Map<Integer,String> giftNameMap;//<序号,name>
    private SfUserTurnTable userTurnTable;
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

    public SfUserTurnTable getUserTurnTable() {
        return userTurnTable;
    }

    public void setUserTurnTable(SfUserTurnTable userTurnTable) {
        this.userTurnTable = userTurnTable;
    }

    public List<SfUserTurnTableRecord> getUserTurnTableRecords() {
        return userTurnTableRecords;
    }

    public void setUserTurnTableRecords(List<SfUserTurnTableRecord> userTurnTableRecords) {
        this.userTurnTableRecords = userTurnTableRecords;
    }

    public Map<Integer, Integer> getGiftIdMap() {
        return giftIdMap;
    }

    public void setGiftIdMap(Map<Integer, Integer> giftIdMap) {
        this.giftIdMap = giftIdMap;
    }

    public Map<Integer, String> getGiftNameMap() {
        return giftNameMap;
    }

    public void setGiftNameMap(Map<Integer, String> giftNameMap) {
        this.giftNameMap = giftNameMap;
    }
}
