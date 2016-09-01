package com.masiis.shop.api.bean.shop;

import com.masiis.shop.api.bean.base.BasePagingRes;
import com.masiis.shop.dao.beans.user.BfSpokesManDetailPo;
import com.masiis.shop.dao.beans.user.SfSpokesAndFansInfo;

import java.util.List;

/**
 * Created by hw on 2016/7/29.
 */
public class SpokesMapRes extends BasePagingRes {

    private Integer totalFansNum;
    private Integer totalSpokesManNum;
    private List<SfSpokesAndFansInfo> infos;
    private BfSpokesManDetailPo detailPo;

    public BfSpokesManDetailPo getDetailPo() { return detailPo; }

    public void setDetailPo(BfSpokesManDetailPo detailPo) { this.detailPo = detailPo; }

    public List<SfSpokesAndFansInfo> getInfos() {
        return infos;
    }

    public void setInfos(List<SfSpokesAndFansInfo> infos) {
        this.infos = infos;
    }

    public Integer getTotalFansNum() {
        return totalFansNum;
    }

    public void setTotalFansNum(Integer totalFansNum) {
        this.totalFansNum = totalFansNum;
    }

    public Integer getTotalSpokesManNum() {
        return totalSpokesManNum;
    }

    public void setTotalSpokesManNum(Integer totalSpokesManNum) {
        this.totalSpokesManNum = totalSpokesManNum;
    }
}
