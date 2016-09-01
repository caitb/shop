package com.masiis.shop.api.bean.family;

import com.masiis.shop.api.bean.base.BaseBusinessRes;
import com.masiis.shop.dao.beans.family.MyTeamListHomePo;

public class MyTeamRes extends BaseBusinessRes {

    private MyTeamListHomePo myTeamListHomePo;

    public MyTeamListHomePo getMyTeamListHomePo() {
        return myTeamListHomePo;
    }

    public void setMyTeamListHomePo(MyTeamListHomePo myTeamListHomePo) {
        this.myTeamListHomePo = myTeamListHomePo;
    }

}
