package com.masiis.shop.web.platform.controller.user;

import com.masiis.shop.dao.po.ComUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 个人中心
 * Created by cai_tb on 16/3/16.
 */
@Controller
@RequestMapping("/profile")
public class ProfileController {

    @RequestMapping("/profile")
    public ModelAndView profile(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = new ModelAndView("platform/user/profile");
        HttpSession session = request.getSession();
        ComUser comUser = (ComUser) session.getAttribute("comUser");
        mav.addObject("comUser",comUser);
        return mav;
    }
}
