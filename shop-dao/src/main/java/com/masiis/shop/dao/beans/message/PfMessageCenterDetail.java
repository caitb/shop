package com.masiis.shop.dao.beans.message;

/**
 * @Date 2016/7/12
 * @Author lzh
 */
public class PfMessageCenterDetail {
    private Integer isSeeNum;
    private String headUrl;
    private String fromUserName;
    private String fromUserId;
    private String latestMessage;

    public Integer getIsSeeNum() {
        return isSeeNum;
    }

    public void setIsSeeNum(Integer isSeeNum) {
        this.isSeeNum = isSeeNum;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getLatestMessage() {
        return latestMessage;
    }

    public void setLatestMessage(String latestMessage) {
        this.latestMessage = latestMessage;
    }
}
