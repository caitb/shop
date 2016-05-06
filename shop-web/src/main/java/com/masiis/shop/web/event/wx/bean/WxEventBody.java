package com.masiis.shop.web.event.wx.bean;

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
}
