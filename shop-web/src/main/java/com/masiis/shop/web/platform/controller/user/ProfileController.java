package com.masiis.shop.web.platform.controller.user;

import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.web.platform.constants.SysConstants;
import com.masiis.shop.web.platform.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 个人中心
 * Created by cai_tb on 16/3/16.
 */
@Controller
@RequestMapping("/profile")
public class ProfileController extends BaseController{

    @RequestMapping("/profile")
    public ModelAndView profile(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = new ModelAndView("platform/user/profile");
        ComUser user = getComUser(request);
        mav.addObject("user",user);
        return mav;
    }
}
