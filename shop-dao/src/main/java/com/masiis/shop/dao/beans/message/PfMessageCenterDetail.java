package com.masiis.shop.dao.beans.message;

import com.masiis.shop.common.util.EmojiUtils;

/**
 * @Date 2016/7/12
 * @Author lzh
 */
public class PfMessageCenterDetail {
    private Integer notSeeNum;
    private String headUrl;
    private String fromUserName;
    private String fromUserId;
    private String latestMessage;

    public Integer getNotSeeNum() {
        return notSeeNum;
    }

    public void setNotSeeNum(Integer notSeeNum) {
        this.notSeeNum = notSeeNum;
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
        this.latestMessage = EmojiUtils.parseEmojiToUnicode(latestMessage);
    }
}
