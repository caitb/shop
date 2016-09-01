package com.masiis.shop.dao.beans.user;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * B端收入记录
 * Created by wangbingjian on 2016/7/28.
 */
public class PfIncomRecordPo {
    private static final NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.CHINA);
    /**
     * 总收入
     */
    private BigDecimal totalIncom;
    /**
     * 总收入页面展示
     */
    private String totalIncomView;
    /**
     * 页码
     */
    private Integer pageNum;
    /**
     * 总数量
     */
    private Long totalCount;
    /**
     * 总页数
     */
    private Integer totalPage;
    /**
     * 每页展示条数
     */
    private Integer pageSize;

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

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotalIncom(BigDecimal totalIncom) {
        this.totalIncom = totalIncom;
        setTotalIncomView(numberFormat.format(totalIncom));
    }

    public String getTotalIncomView() {
        return totalIncomView;
    }

    public void setTotalIncomView(String totalIncomView) {
        this.totalIncomView = totalIncomView;
    }

    public List<PfIncomRecord> getPfIncomRecords() {
        return pfIncomRecords;
    }

    public void setPfIncomRecords(List<PfIncomRecord> pfIncomRecords) {
        this.pfIncomRecords = pfIncomRecords;
    }
}
