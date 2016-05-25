package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BaseBusinessRes;

import java.util.Map;

/**
 * Created by cai_tb on 16/5/25.
 */
public class MemberInfoRes extends BaseBusinessRes {

    private Map<String, Object> memberMap;

    public Map<String, Object> getMemberMap() {
        return memberMap;
    }

    public void setMemberMap(Map<String, Object> memberMap) {
        this.memberMap = memberMap;
    }
}
