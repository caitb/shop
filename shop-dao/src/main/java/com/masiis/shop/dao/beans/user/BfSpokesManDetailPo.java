package com.masiis.shop.dao.beans.user;

/**
 * B端代言人详情展示po
 * Created by wangbingjian on 2016/7/8.
 */
public class BfSpokesManDetailPo {
    /**
     * 一级粉丝
     */
    private Integer firstFansNum = 0;
    /**
     * 二级粉丝
     */
    private Integer secondFansNum = 0;
    /**
     * 三级粉丝
     */
    private Integer thirdFansNum = 0;
    /**
     * 一级代言人
     */
    private Integer firstSpokesManNum = 0;
    /**
     * 二级代言人
     */
    private Integer secondSpokesManNum = 0;
    /**
     * 详情
     */
    private SfSpokesAndFansInfo info;

    public Integer getFirstFansNum() {
        return firstFansNum;
    }

    public void setFirstFansNum(Integer firstFansNum) {
        this.firstFansNum = firstFansNum;
    }

    public Integer getSecondFansNum() {
        return secondFansNum;
    }

    public void setSecondFansNum(Integer secondFansNum) {
        this.secondFansNum = secondFansNum;
    }

    public Integer getThirdFansNum() {
        return thirdFansNum;
    }

    public void setThirdFansNum(Integer thirdFansNum) {
        this.thirdFansNum = thirdFansNum;
    }

    public Integer getFirstSpokesManNum() {
        return firstSpokesManNum;
    }

    public void setFirstSpokesManNum(Integer firstSpokesManNum) {
        this.firstSpokesManNum = firstSpokesManNum;
    }

    public Integer getSecondSpokesManNum() {
        return secondSpokesManNum;
    }

    public void setSecondSpokesManNum(Integer secondSpokesManNum) {
        this.secondSpokesManNum = secondSpokesManNum;
    }

    public SfSpokesAndFansInfo getInfo() {
        return info;
    }

    public void setInfo(SfSpokesAndFansInfo info) {
        this.info = info;
    }
}
