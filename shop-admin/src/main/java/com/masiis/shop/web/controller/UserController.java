package com.masiis.shop.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import com.masiis.shop.common.util.KeysUtil;
import com.masiis.shop.dao.pbuser.PbUser;
import com.masiis.shop.dao.pbuser.PbUserExample;
import com.masiis.shop.service.PbUserService;
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
    private PbUserService pbUserService;

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
     * @param pbUser     登陆参数
     * @return
     */
    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response, PbUser pbUser) throws JsonProcessingException {
        ModelAndView mav = new ModelAndView();

        /* 已登陆 */
        HttpSession session = request.getSession();
        if (!session.isNew() && session.getAttribute("user") != null) {
            mav.setViewName("redirect:/main/index");
            return mav;
        }

        /* 未登陆 */

        //用户名或密码为空
        if (StringUtil.isEmpty(pbUser.getUserName()) || StringUtil.isEmpty(pbUser.getPassword())) {
            mav.setViewName("redirect:login.shtml");
            mav.addObject("user", pbUser);
            return mav;
        }

        PbUserExample sysUserExample = new PbUserExample();
        sysUserExample
                .createCriteria()
                .andUserNameEqualTo(pbUser.getUserName())
                .andPasswordEqualTo(KeysUtil.md5Encrypt(pbUser.getPassword()));

        List<PbUser> sysUsers = this.pbUserService.findByExample(sysUserExample);

        //用户名或密码不对
        if (sysUsers == null || sysUsers.size() <= 0) {
            mav.setViewName("redirect:login.shtml");
            mav.addObject("user", pbUser);
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
        PbUserExample sysUserExample = new PbUserExample();
        PbUserExample.Criteria criteria = sysUserExample.createCriteria();
        if (StringUtil.isNotEmpty(userName)){
            criteria.andUserNameEqualTo(userName);
        }
        if(StringUtil.isNotEmpty(phone)){
            criteria.andPhoneEqualTo(phone);
        }

        PageHelper.startPage(pageNum, pageSize);
        List<PbUser> sysUsers = this.pbUserService.findByExample(sysUserExample);
        PageInfo<PbUser> pageInfo = new PageInfo<>(sysUsers);

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
     * @param pbUser     新添用户数据
     */
    @RequestMapping("/add")
    @ResponseBody
    public String add(HttpServletRequest request, HttpServletResponse response, PbUser pbUser) {
        if (StringUtil.isNotEmpty(pbUser.getPassword())) {
            pbUser.setPassword(KeysUtil.md5Encrypt(pbUser.getPassword()));
        }

        if (pbUser.getId() == null) {
            this.pbUserService.add(pbUser);
        } else {
            this.pbUserService.updateByPrimaryKey(pbUser);
        }

        return "保存成功";
    }

}
