package com.masiis.shop.api.bean.family;

import com.masiis.shop.api.bean.base.BaseBusinessRes;
import com.masiis.shop.dao.beans.family.TeamListPoPaging;

/**
 * 团队列表
 * Created by wangbingjian on 2016/8/23.
 */
public class TeamListRes extends BaseBusinessRes {

    private TeamListPoPaging teamListPoPaging;

    public TeamListPoPaging getTeamListPoPaging() {
        return teamListPoPaging;
    }

    public void setTeamListPoPaging(TeamListPoPaging teamListPoPaging) {
        this.teamListPoPaging = teamListPoPaging;
    }
}
