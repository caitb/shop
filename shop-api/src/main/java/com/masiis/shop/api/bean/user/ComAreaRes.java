/*
 * ComArea.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BaseBusinessReq;
import com.masiis.shop.api.bean.base.BaseBusinessRes;
import com.masiis.shop.dao.po.ComArea;

import java.util.Date;
import java.util.List;

/**
 * 地址表
 * 
 * @author masiis
 * @version 1.0 2016-03-03
 */
public class ComAreaRes extends BaseBusinessRes {

    private List<ComArea> comAreas;

    public List<ComArea> getComAreas() {
        return comAreas;
    }

    public void setComAreas(List<ComArea> comAreas) {
        this.comAreas = comAreas;
    }
}