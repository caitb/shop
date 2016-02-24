package com.masiis.shop.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import com.masiis.shop.common.util.KeysUtil;
import com.masiis.shop.dao.user.SysUser;
import com.masiis.shop.dao.user.SysUserExample;
import com.masiis.shop.dao.user.SysUserExample.Criteria;
import com.masiis.shop.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cai_tb on 16/2/16.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 登陆页面
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/login.shtml")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        return "user/login";
    }

    /**
     * 用户列表页面
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/list.shtml")
    public String list(HttpServletRequest request, HttpServletResponse response) {
        return "user/userList";
    }

    /**
     * 添加用户页面
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/add.shtml")
    public String add(HttpServletRequest request, HttpServletResponse response) {
        return "user/addUser";
    }

    /**
     * 登陆逻辑
     *
     * @param request
     * @param response
     * @param sysUser     登陆参数
     * @return
     */
    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response, SysUser sysUser) throws JsonProcessingException {
        ModelAndView mav = new ModelAndView();

        /* 已登陆 */
        HttpSession session = request.getSession();
        if (!session.isNew() && session.getAttribute("user") != null) {
            mav.setViewName("redirect:/main/index");
            return mav;
        }

        /* 未登陆 */

        //用户名或密码为空
        if (StringUtil.isEmpty(sysUser.getUserName()) || StringUtil.isEmpty(sysUser.getPassword())) {
            mav.setViewName("redirect:login.shtml");
            mav.addObject("user", sysUser);
            return mav;
        }

        SysUserExample sysUserExample = new SysUserExample();
        sysUserExample
                .createCriteria()
                .andUserNameEqualTo(sysUser.getUserName())
                .andPasswordEqualTo(KeysUtil.md5Encrypt(sysUser.getPassword()));

        List<SysUser> sysUsers = this.userService.findByExample(sysUserExample);

        //用户名或密码不对
        if (sysUsers == null || sysUsers.size() <= 0) {
            mav.setViewName("redirect:login.shtml");
            mav.addObject("user", sysUser);
            return mav;
        }

        //登陆成功
        session.setAttribute("user", sysUsers.get(0));
        mav.setViewName("redirect:/main/index");

        return mav;
    }

    /**
     * 条件分页查询用户数据
     *
     * @param request
     * @param response
     * @param userName 用户名
     * @param phone    电话号码
     * @param pageNum  当前页
     * @param pageSize 每页显示条数
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response,
                       String userName,
                       String phone,
                       Integer pageNum,
                       Integer pageSize
    ) throws JsonProcessingException {

        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 10 : pageSize;

        //添加查询条件
        SysUserExample sysUserExample = new SysUserExample();
        Criteria criteria = sysUserExample.createCriteria();
        if (StringUtil.isNotEmpty(userName)){
            criteria.andUserNameEqualTo(userName);
        }
        if(StringUtil.isNotEmpty(phone)){
            criteria.andPhoneEqualTo(phone);
        }

        PageHelper.startPage(pageNum, pageSize);
        List<SysUser> sysUsers = this.userService.findByExample(sysUserExample);
        PageInfo<SysUser> pageInfo = new PageInfo<>(sysUsers);

        Map<String, Object> usersMap = new HashMap<>();
        usersMap.put("total", pageInfo.getTotal());
        usersMap.put("rows", sysUsers);

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(usersMap);

        //return usersMap;
    }

    /**
     * 添加用户
     *
     * @param request
     * @param response
     * @param sysUser     新添用户数据
     */
    @RequestMapping("/add")
    @ResponseBody
    public String add(HttpServletRequest request, HttpServletResponse response, SysUser sysUser) {
        if (StringUtil.isNotEmpty(sysUser.getPassword())) {
            sysUser.setPassword(KeysUtil.md5Encrypt(sysUser.getPassword()));
        }

        if (sysUser.getId() == null) {
            this.userService.addSysUser(sysUser);
        } else {
            this.userService.updateSysUserById(sysUser);
        }

        return "保存成功";
    }

}
