package com.masiis.shop.admin.controller.system;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.masiis.shop.admin.service.system.PbMenuService;
import com.masiis.shop.admin.service.system.PbUserMenuService;
import com.masiis.shop.dao.po.PbMenu;
import com.masiis.shop.dao.po.PbUser;
import com.masiis.shop.dao.po.PbUserMenu;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 菜单栏
 * Created by ZhaoLiang on 2016/2/18.
 */
@Controller
@RequestMapping("/menu")
public class MenuController {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Resource
    private PbMenuService pbMenuService;
    @Resource
    private PbUserMenuService pbUserMenuService;

    @RequestMapping("/menu.shtml")
    public String menu(){
        return "system/menu";
    }

    @RequestMapping("/treeMenu.shtml")
    public ModelAndView treeMenu(HttpServletRequest request, HttpServletResponse response, Long userId) throws JsonProcessingException {

        ModelAndView mav = new ModelAndView("system/tree_menu");

        List<PbMenu> pbMenus = pbMenuService.findByCondition(new PbMenu());
        String pbMenusJson = objectMapper.writeValueAsString(pbMenus);

        PbUserMenu pbUserMenuC = new PbUserMenu();
        pbUserMenuC.setPbUserId(userId);
        List<PbUserMenu> pbUserMenus = pbUserMenuService.findByCondition(pbUserMenuC);
        String pbUserMenusJson = objectMapper.writeValueAsString(pbUserMenus);

        mav.addObject("pbMenusJson", pbMenusJson);
        mav.addObject("pbUserMenusJson", pbUserMenusJson);
        mav.addObject("userId", userId);

        return mav;
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response){

        HttpSession session = request.getSession();
        PbUser pbUser = (PbUser)session.getAttribute("pbUser");
        List<PbMenu> pbMenus = pbMenuService.findByPbUserId(pbUser.getId());

        return pbMenus;
    }

}
