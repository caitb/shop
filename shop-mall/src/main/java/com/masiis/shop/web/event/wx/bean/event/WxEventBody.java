package com.masiis.shop.web.event.wx.bean.event;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @Date 2016/5/6
 * @Auther lzh
 */
@XStreamAlias("xml")
public class WxEventBody extends WxBaseEvent{
    @XStreamAlias("EventKey")
    private String eventKey;
    @XStreamAlias("Ticket")
    private String ticket;
    @XStreamAlias("Status")
    private String status;
    @XStreamAlias("Content")
    private String content;
    @XStreamAlias("MsgID")
    private String msgID;
    @XStreamAlias("MenuId")
    private String menuId;
    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMsgID() {
        return msgID;
    }

    public void setMsgID(String msgID) {
        this.msgID = msgID;
    }
}