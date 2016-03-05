package com.masiis.shop.admin.controller.system;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import com.masiis.shop.admin.service.system.PbUserMenuService;
import com.masiis.shop.admin.service.system.PbUserService;
import com.masiis.shop.common.util.KeysUtil;
import com.masiis.shop.dao.po.PbUser;
import com.masiis.shop.dao.po.PbUserMenu;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
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
    @Resource
    private PbUserMenuService pbUserMenuService;

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
        return "user/list";
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
            mav.setViewName("redirect:/main/index.shtml");
            return mav;
        }

        /* 未登陆 */

        //用户名或密码为空
        if (StringUtil.isEmpty(pbUser.getUserName()) || StringUtil.isEmpty(pbUser.getPassword())) {
            mav.setViewName("redirect:login.shtml");
            mav.addObject("pbUser", pbUser);
            return mav;
        }


        List<PbUser> pbUsers = this.pbUserService.findByCondition(pbUser);

        //用户名或密码不对
        if (pbUsers == null || pbUsers.size() <= 0) {
            mav.setViewName("redirect:login.shtml");
            mav.addObject("pbUser", pbUser);
            return mav;
        }

        //登陆成功
        session.setAttribute("pbUser", pbUsers.get(0));
        mav.setViewName("redirect:/main/index.shtml");

        return mav;
    }

    /**
     * 退出
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/loginOut.shtml")
    public String loginOut(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute("pbUser");
        return "user/login";
    }
    /**
     * 条件分页查询用户数据
     * @param request
     * @param response
     * @param search
     * @param sort
     * @param order
     * @param offset
     * @param limit
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response,
                       String search,
                       String sort,
                       String order,
                       Integer offset,
                       Integer limit
    ) throws JsonProcessingException {

        Map<String, Object> pbUsersMap = new HashMap<>();


        offset = offset==null ? 0 : offset;
        limit  = limit ==null ? 0 : limit;
        Integer pageNo = offset/10 + 1;
        if(StringUtils.isNotBlank(sort)){
            if("userName".equals(sort)) sort = "user_name";
            if("trueName".equals(sort)) sort = "true_name";
            PageHelper.startPage(pageNo, limit, sort + " " + order);
        }else{
            PageHelper.startPage(pageNo, limit);
        }

        PbUser pbUserC = new PbUser();

        List<PbUser> pbUsers = pbUserService.findByCondition(pbUserC);
        PageInfo<PbUser> pageInfo = new PageInfo<>(pbUsers);

        pbUsersMap.put("total", pageInfo.getTotal());
        pbUsersMap.put("rows", pbUsers);

        return pbUsersMap;
    }

    @RequestMapping("/load.shtml")
    public ModelAndView load(HttpServletRequest request, HttpServletResponse response, Long id){
        ModelAndView mav = new ModelAndView("user/edit");

        PbUser pbUser = null;
        if(id != null){
            pbUser = pbUserService.findByid(id);
        }

        mav.addObject("pbUser", pbUser);

        return mav;
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
            this.pbUserService.updateById(pbUser);
        }

        return "保存成功";
    }

    /**
     * 保存用户菜单权限信息
     * @param request
     * @param response
     * @param userId
     * @param pbMenuIds
     * @return
     */
    @RequestMapping("/updateUserMenu.do")
    @ResponseBody
    public String updateUserMenu(HttpServletRequest request, HttpServletResponse response,
                                 Long userId,
                                 @RequestParam(value = "pbMenuIds[]") Long[] pbMenuIds){

        pbUserMenuService.deleteByPbUserId(userId);

        PbUserMenu pbUserMenu = new PbUserMenu();
        for(Long pbMenuId : pbMenuIds){
            pbUserMenu.setPbUserId(userId);
            pbUserMenu.setPbMenuId(pbMenuId);
            pbUserMenu.setCreateTime(new Date());
            pbUserMenu.setUpdateTime(new Date());
            pbUserMenuService.add(pbUserMenu);
        }

        return "success";
    }

}
