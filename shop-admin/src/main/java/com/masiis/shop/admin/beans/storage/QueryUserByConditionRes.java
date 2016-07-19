package com.masiis.shop.admin.beans.storage;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

/**
 * @Date 2016/7/19
 * @Author lzh
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class QueryUserByConditionRes {
    private String resCode;
    private String resMsg;
    private List<StorageUserInfo> users;

    public List<StorageUserInfo> getUsers() {
        return users;
    }

    public void setUsers(List<StorageUserInfo> users) {
        this.users = users;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }
}
