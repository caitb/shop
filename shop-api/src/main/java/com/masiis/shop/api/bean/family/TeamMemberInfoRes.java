package com.masiis.shop.api.bean.family;

import com.masiis.shop.api.bean.base.BaseBusinessRes;
import com.masiis.shop.dao.beans.family.TeamMemberInfo;

/**
 * Created by wangbingjian on 2016/8/23.
 */
public class TeamMemberInfoRes extends BaseBusinessRes {

    private TeamMemberInfo teamMemberInfo;

    public TeamMemberInfo getTeamMemberInfo() {
        return teamMemberInfo;
    }

    public void setTeamMemberInfo(TeamMemberInfo teamMemberInfo) {
        this.teamMemberInfo = teamMemberInfo;
    }
}
