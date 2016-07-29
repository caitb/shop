package com.masiis.shop.dao.beans.user;

import java.math.BigDecimal;
import java.util.List;

/**
 * B端收入记录
 * Created by wangbingjian on 2016/7/28.
 */
public class PfIncomRecordPo {
    /**
     * 总收入
     */
    private BigDecimal totalIncom;
    /**
     * 页码
     */
    private Integer pageNum;
    /**
     * 总数量
     */
    private Long totalCount;

    private List<PfIncomRecord> pfIncomRecords;

    public BigDecimal getTotalIncom() {
        return totalIncom;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public void setTotalIncom(BigDecimal totalIncom) {
        this.totalIncom = totalIncom;
    }

    public List<PfIncomRecord> getPfIncomRecords() {
        return pfIncomRecords;
    }

    public void setPfIncomRecords(List<PfIncomRecord> pfIncomRecords) {
        this.pfIncomRecords = pfIncomRecords;
    }
}
