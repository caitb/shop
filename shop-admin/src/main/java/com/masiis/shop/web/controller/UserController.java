package com.masiis.shop.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import com.masiis.shop.dao.login.mapper.User;
import com.masiis.shop.service.login.UserService;
import com.masiis.shop.web.utils.KeysUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by cai_tb on 16/2/16.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/toLogin")
    public String toLogin(HttpServletRequest request, HttpServletResponse response){
        return "user/login";
    }

    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response, User user){
        ModelAndView mav = new ModelAndView();

        /* 已登陆 */
        HttpSession session = request.getSession();
        if(!session.isNew()){
            mav.setViewName("user/index");
            return mav;
        }

        /* 未登陆 */

        //用户名或密码为空
        if(StringUtil.isEmpty(user.getUserName()) || StringUtil.isEmpty(user.getPassword())){
            mav.setViewName("redirect:user/login");
            mav.addObject("user", user);
        }

        User u = this.userService.findByUserNameAndPwd(user.getUserName(), KeysUtil.md5Encrypt(user.getPassword()));

        //用户名或密码不对
        if (u == null){
            mav.setViewName("redirect:user/login");
            mav.addObject("user", user);
        }

        //登陆成功
        session.setAttribute("user", user);
        mav.setViewName("index");
        return mav;
    }

    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response,
                             User user,
                             Integer pageNum,
                             Integer pageSize
                            ){
        pageNum  = pageNum  == null ? 1  : pageNum;
        pageSize = pageSize == null ? 10 : pageSize;

        PageHelper.startPage(pageNum, pageSize);
        List<User> users = this.userService.listUserByCondition(user);
        PageInfo<User> pageInfo = new PageInfo<>();

        ModelAndView mav = new ModelAndView("user/userList");
        mav.addObject("pageInfo", pageInfo);
        return mav;
    }

    @RequestMapping("/add")
    public void add(HttpServletRequest request, HttpServletResponse response, User user){
        if (StringUtil.isNotEmpty(user.getPassword())){
            user.setPassword(KeysUtil.md5Encrypt(user.getPassword()));
        }

        this.userService.addUser(user);
    }
}
