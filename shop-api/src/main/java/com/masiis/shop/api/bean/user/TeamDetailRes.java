package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BaseBusinessRes;

import java.util.Map;

/**
 * Created by cai_tb on 16/5/25.
 */
public class TeamDetailRes extends BaseBusinessRes {

    private Map<String, Object> teamMap;

    public Map<String, Object> getTeamMap() {
        return teamMap;
    }

    public void setTeamMap(Map<String, Object> teamMap) {
        this.teamMap = teamMap;
    }
}