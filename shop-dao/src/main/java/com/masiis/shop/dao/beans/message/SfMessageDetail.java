package com.masiis.shop.dao.beans.message;

import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.dao.po.SfMessageContent;
import com.masiis.shop.dao.po.SfMessageSrRelation;

import java.util.Date;

/**
 * @Date 2016/7/5
 * @Author lzh
 */
public class SfMessageDetail extends SfMessageSrRelation{
    private Date createTime;
    private SfMessageContent content;
    private String createTimeView;

    public SfMessageContent getContent() {
        return content;
    }

    public void setContent(SfMessageContent content) {
        this.content = content;
    }

    public String getCreateTimeView() {
        return createTimeView;
    }

    public void setCreateTimeView(String createTimeView) {
        this.createTimeView = createTimeView;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
        if(this.createTime != null){
            this.createTimeView = DateUtil.Date2String(this.createTime, DateUtil.SQL_TIME_FMT);
        }
    }
}
