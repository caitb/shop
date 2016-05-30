package com.masiis.shop.admin.controller.base;

import com.masiis.shop.dao.po.PbUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by ZhaoLiang on 2016/3/2.
 */
public class BaseController {

    public PbUser getPbUser(HttpServletRequest request){
        PbUser pbUser = null;

        if(request == null){
            return pbUser;
        }

        pbUser = (PbUser)request.getSession().getAttribute("pbUser");

        return pbUser;
    }
}
