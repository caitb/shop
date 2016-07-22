package com.masiis.shop.admin.beans.storage;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.masiis.shop.admin.beans.base.BaseRes;
import com.masiis.shop.dao.po.ComUser;

import java.util.List;

/**
 * @Date 2016/7/19
 * @Author lzh
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class QueryUserByConditionRes extends BaseRes{
    private List<ComUser> users;

    public List<ComUser> getUsers() {
        return users;
    }

    public void setUsers(List<ComUser> users) {
        this.users = users;
    }
}
